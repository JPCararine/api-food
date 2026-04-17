package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoInputDTO;
import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoResumoAdminDTO;
import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.api.v1.assembler.ItemPedidoDTODisassembler;
import com.algaworks.algafood.api.v1.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.v1.assembler.PedidoDTODisassembler;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.exception.NotFound.*;
import com.algaworks.algafood.domain.exception.NotFound.PedidoNotFoundExceptionId;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.infrastructure.repository.*;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import com.algaworks.algafood.infrastructure.spec.PedidoSpecs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoDTOAssembler pedidoDTOAssembler;
    private final PedidoDTODisassembler pedidoDTODisassembler;
    private final UsuarioRepository usuarioRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ItemPedidoDTODisassembler  itemPedidoDTODisassembler;
    private final AlgaSecurity algaSecurity;
    private final ProdutoRepository produtoRepository;



    public List<PedidoResumoAdminDTO> listAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoDTOAssembler::toAdminDTO)
                .toList();
    }
    public Page<PedidoResumoAdminDTO> consultaFiltroAdmin(PedidoFilter pedidoFilter, Pageable pageable) {
        pageable = traduzirPageable(pageable);
        return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable)
                .map(pedidoDTOAssembler::toAdminDTO);
    }
    public List<PedidoResumoDTO> listarPedidos(Long restauranteId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);


        return restaurante.getPedidos().stream()
                .map(pedidoDTOAssembler::toDTO)
                .toList();
    }
    public List<PedidoResumoDTO> consultaFiltroNormal(Long restauranteId, PedidoFilter pedidoFilter) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        pedidoFilter.setRestauranteId(restauranteId);
        return pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter))
                .stream()
                .map(pedidoDTOAssembler::toDTO)
                .toList();
    }
    public PedidoResumoDTO findByCodigo(String codigo) {
        Pedido pedido = buscarOuFalharCodigo(codigo);
        return pedidoDTOAssembler.toDTO(pedido);
    }
    public PedidoResumoDTO findById(Long id) {
        Pedido pedido = buscarOuFalharId(id);
        return pedidoDTOAssembler.toDTO(pedido);
    }
    public PedidoResumoAdminDTO findByIdDetalhado(String codigo) {
        Pedido pedido = pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PedidoNotFoundExceptionCodigo(codigo));
        return pedidoDTOAssembler.toAdminDTO(pedido);
    }
    public List<PedidoResumoDTO> findByClienteId(Long id) {
        Usuario usuario = buscarUsuarioOuFalhar(id);
        return usuario.getPedidos().stream()
                .map(pedidoDTOAssembler::toDTO)
                .toList();
    }
    @Transactional
    public PedidoResumoDTO criar(PedidoInputDTO pedidoInputDTO) {
        Restaurante restaurante = buscarRestauranteOuFalhar(pedidoInputDTO.getRestauranteId());

        Usuario usuario = buscarUsuarioOuFalhar(algaSecurity.getUsuarioId());

        Pedido pedido = pedidoDTODisassembler.toEntity(pedidoInputDTO);


        pedido.setRestaurante(restaurante);

        pedido.setUsuario(usuario);


        List<ItemPedido> itens = pedidoInputDTO.getItens().stream()
                        .map(itemPedidoDTODisassembler::toEntity)
                .map(item -> {
                    Produto produto = buscarProdutoOuFalhar(item.getProduto().getId());
                    if(!produto.getRestaurante().getId().equals(restaurante.getId())) {
                        throw new RuntimeException("Produto não pertence ao restaurante");
                    }
                    if(!produto.isAtivo()) {
                        throw new RuntimeException("Produto não está mais disponível");
                    }
                    item.setProduto(produto);
                    item.setPedido(pedido);
                    item.setPrecoUnitario(item.getProduto().getPreco());

                    return item;
                })
                        .toList();

        pedido.setItens(itens);


        FormaPagamento formaPagamento = buscarFormaPagamentoOuFalhar(pedidoInputDTO.getFormaPagamento().getId());

        checarSeExisteFormaPagamento(restaurante.getId(), formaPagamento.getId());

        pedido.setFormaPagamento(formaPagamento);

        pedido.setTaxaFrete(restaurante.getTaxaFrete());

        calculoTotal(pedido);

        pedido.setStatus(StatusPedido.CRIADO);
        pedido.setCodigo(UUID.randomUUID().toString());
        pedido.setDataCriacao(OffsetDateTime.now());


        return pedidoDTOAssembler.toDTO(pedidoRepository.save(pedido));


    }

    public void checarSeExisteFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = buscarRestauranteOuFalhar(restauranteId);

        FormaPagamento formaPagamento = buscarFormaPagamentoOuFalhar(formaPagamentoId);

        if(!restaurante.getFormaPagamentos().contains(formaPagamento)) {
            throw new RuntimeException("Esse restaurante não possui essa forma de pagamento");
        }

    }
    public void calculoTotal(Pedido pedido) {

        BigDecimal subTotal = pedido.getItens()
                .stream()
                .peek(itens -> {
                            BigDecimal precoTotal = itens.getPrecoUnitario()
                                    .multiply(BigDecimal.valueOf(itens.getQuantidade()));
                            itens.setPrecoTotal(precoTotal);
                        })
                        .map(ItemPedido::getPrecoTotal)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);


        pedido.setSubtotal(subTotal);
        pedido.setValorTotal(subTotal.add(pedido.getTaxaFrete()));


    }
    @Transactional
    public void confirmar(Long restauranteId, String codigo) {
        Pedido pedido = buscarOuFalharCodigoAndRestauranteId(codigo, restauranteId);
        pedido.confirmar();
        pedidoRepository.save(pedido);
    }
    @Transactional
    public void entregar(Long restauranteId, String codigo) {
        Pedido pedido = buscarOuFalharCodigoAndRestauranteId(codigo, restauranteId);
        pedido.entregar();
    }
    @Transactional
    public void cancelar(Long restauranteId, String codigo) {
        Pedido pedido = buscarOuFalharCodigoAndRestauranteId(codigo, restauranteId);
        pedido.cancelar();
        pedidoRepository.save(pedido);
    }

    private Pageable traduzirPageable(Pageable pageable) {
        var mapeamento = Map.ofEntries(
                Map.entry("id", "id"),
                Map.entry("codigo", "codigo"),
                Map.entry("subtotal", "subtotal"),
                Map.entry("taxaFrete", "taxaFrete"),
                Map.entry("valorTotal", "valorTotal"),


                Map.entry("dataCriacao", "dataCriacao"),
                Map.entry("dataConfirmacao", "dataConfirmacao"),
                Map.entry("dataCancelamento", "dataCancelamento"),
                Map.entry("dataEntrega", "dataEntrega"),


                Map.entry("status", "status"),


                Map.entry("restauranteId", "restaurante.id"),
                Map.entry("restauranteNome", "restaurante.nome"),


                Map.entry("usuarioId", "usuario.id"),
                Map.entry("nomeUsuario", "usuario.nome"),


                Map.entry("formaPagamentoId", "formaPagamento.id"),
                Map.entry("formaPagamentoDescricao", "formaPagamento.descricao"));
        return PageableTranslator.translate(pageable, mapeamento);
    }

    private Restaurante buscarRestauranteOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
    }
    private FormaPagamento buscarFormaPagamentoOuFalhar(Long id) {
        return formaPagamentoRepository.findById(id)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(id));
    }
    private Usuario buscarUsuarioOuFalhar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
    }
    private Pedido buscarOuFalharCodigo(String codigo) {
        return pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PedidoNotFoundExceptionCodigo(codigo));
    }
    private Pedido buscarOuFalharCodigoAndRestauranteId(String codigo,  Long restauranteId) {
        return pedidoRepository.findByCodigoAndRestauranteId(codigo, restauranteId)
                .orElseThrow(() -> new PedidoNotFoundExceptionCodigo(codigo));
    }
    private Pedido buscarOuFalharId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundExceptionId(id));
    }
    private Produto buscarProdutoOuFalhar(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
    }



}
