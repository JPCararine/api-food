package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.ItemPedido.ItemPedidoInputDTO;
import com.algaworks.algafood.api.DTO.Pedido.PedidoInputDTO;
import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoAdminDTO;
import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.api.assembler.ItemPedidoDTODisassembler;
import com.algaworks.algafood.api.assembler.PedidoDTOAssembler;
import com.algaworks.algafood.api.assembler.PedidoDTODisassembler;
import com.algaworks.algafood.domain.exception.NotFound.FormaPagamentoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.PedidoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.UsuarioNotFoundException;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.infrastructure.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoDTOAssembler pedidoDTOAssembler;
    private final PedidoDTODisassembler pedidoDTODisassembler;
    private final UsuarioRepository usuarioRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final RestauranteRepository restauranteRepository;
    private final ItemPedidoDTODisassembler  itemPedidoDTODisassembler;

    public List<PedidoResumoAdminDTO> listAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoDTOAssembler::toAdminDTO)
                .toList();
    }
    public PedidoResumoDTO findById(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        return pedidoDTOAssembler.toDTO(pedido);
    }
    public PedidoResumoAdminDTO findByIdDetalhado(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        return pedidoDTOAssembler.toAdminDTO(pedido);
    }
    public List<PedidoResumoDTO> findByClienteId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));
        return usuario.getPedidos().stream()
                .map(pedidoDTOAssembler::toDTO)
                .toList();
    }
    @Transactional
    public PedidoResumoDTO criar(Long restauranteId, PedidoInputDTO pedidoInputDTO) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));

        Pedido pedido = pedidoDTODisassembler.toEntity(pedidoInputDTO);

        pedido.setRestaurante(restaurante);

        Usuario usuario = usuarioRepository.findById(pedidoInputDTO.getUsuario().getId())
                .orElseThrow(() -> new UsuarioNotFoundException(pedidoInputDTO.getUsuario().getId()));

        pedido.setUsuario(usuario);


        List<ItemPedido> itens = pedidoInputDTO.getItens().stream()
                        .map(itemPedidoDTODisassembler::toEntity)
                .peek(item -> {
                    item.setPedido(pedido);
                    item.setPrecoUnitario(item.getProduto().getPreco());
                        })
                        .toList();

        pedido.setItens(itens);

        FormaPagamento formaPagamento = formaPagamentoRepository.findById(pedidoInputDTO.getFormaPagamento().getId())
                .orElseThrow(() -> new FormaPagamentoNotFoundException(pedidoInputDTO.getFormaPagamento().getId()));

        checarSeExisteFormaPagamento(restaurante.getId(), formaPagamento.getId());

        pedido.setFormaPagamento(formaPagamento);

        pedido.setTaxaFrete(restaurante.getTaxaFrete());

        calculoTotal(pedido);

        pedido.setStatus(StatusPedido.CRIADO);

        return pedidoDTOAssembler.toDTO(pedidoRepository.save(pedido));


    }

    public void checarSeExisteFormaPagamento(Long restauranteId, Long formaPagamentoId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));

        FormaPagamento formaPagamento = formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(formaPagamentoId));

        if(!restaurante.getFormaPagamentos().contains(formaPagamento)) {
            throw new RuntimeException("Esse restaurante não possui essa forma de pagamento");
        }

    }
    public void calculoTotal(Pedido pedido) {

        BigDecimal subTotal = pedido.getItens()
                .stream()
                .map(itens -> itens.getPrecoUnitario()
                        .multiply(BigDecimal.valueOf(itens.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setSubtotal(subTotal);
        pedido.setValorTotal(subTotal.add(pedido.getTaxaFrete()));


    }

}
