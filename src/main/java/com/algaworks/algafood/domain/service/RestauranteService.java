package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.FormaPagamento.FormaPagamentoDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTO;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTOPut;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDetalhadoDTO;
import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.domain.exception.JaExistente.EntidadeJaExistente;
import com.algaworks.algafood.domain.exception.JaExistente.FormaPagamentoJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.*;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.infrastructure.repository.*;
import com.algaworks.algafood.infrastructure.spec.RestauranteSpecs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final RestauranteDTOAssembler restauranteDTOAssembler;
    private final RestauranteInputDisassembler restauranteInputDisassembler;
    private final CidadeRepository cidadeRepository;
    private final EstadoRepository estadoRepository;
    private final ProdutoDTOAssembler produtoDTOAssembler;
    private final ProdutoRepository produtoRepository;


    public List<RestauranteDTO> listAll() {
        return restauranteRepository.findAll()
                .stream()
                .map(restauranteDTOAssembler::toDTO)
                .toList();
    }

    public RestauranteDTO findById(Long id) {
        return restauranteRepository.findById(id)
                .map(restauranteDTOAssembler::toDTO)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
    }
    public RestauranteDetalhadoDTO findByIdDetalhado(Long id) {
         return restauranteRepository.findById(id)
                .map(restauranteDTOAssembler::toDTODetalhado)
                .orElseThrow(() -> new RestauranteNotFoundException(id));

    }

    public List<RestauranteDTO> findByNome(String nome) {
        if (nome == null) {
            return listAll();
        }
        return restauranteRepository.findByNome(nome)
                .stream()
                .map(restauranteDTOAssembler::toDTO)
                .toList();
    }

    public List<RestauranteDTO> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal)
                .stream()
                .map(restauranteDTOAssembler::toDTO)
                .toList();
    }

    public List<RestauranteDTO> findTaxaGratis(String nome) {
        Specification<Restaurante> spec = Specification.where(RestauranteSpecs.comFreteGratis());

        if (nome != null) {
            spec = spec.and(RestauranteSpecs.comNome(nome));
        }

        List<Restaurante> restaurantes = restauranteRepository.findAll(spec);

        if (restaurantes.isEmpty()) {
            throw new BaseEntityNotFoundException("Nenhum restaurante encontrado");
        }

        return restaurantes.stream()
                .map(restauranteDTOAssembler::toDTO)
                .toList();
    }

    public Optional<RestauranteDTO> primeiroRestaurante() {
        return restauranteRepository.acharPrimeiro()
                .map(restauranteDTOAssembler::toDTO);
    }

    @Transactional
    public RestauranteDTO save(RestauranteDTOPut restauranteDTOPut) {
        checarSeExisteNome(restauranteDTOPut.getNome(), null);

        Cozinha cozinha = cozinhaRepository.findById(restauranteDTOPut.getCozinha().getId())
                .orElseThrow(() -> new CozinhaNotFoundException(restauranteDTOPut.getCozinha().getId()));

        Cidade cidade = cidadeRepository.findById(restauranteDTOPut.getEndereco().getCidade().getId())
                .orElseThrow(() -> new CidadeNotFoundException(restauranteDTOPut.getEndereco().getCidade().getId()));



        Restaurante restaurante = restauranteInputDisassembler.toEntity(restauranteDTOPut);
        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);



        return restauranteDTOAssembler.toDTO(restauranteRepository.save(restaurante));
    }

    @Transactional
    public RestauranteDTO update(Long id, RestauranteDTOPut dto) {

        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));

        checarSeExisteNome(dto.getNome(), restaurante.getId());

        restauranteInputDisassembler.copyToEntity(dto, restaurante);

        Cozinha cozinha = cozinhaRepository.findById(dto.getCozinha().getId())
                .orElseThrow(() -> new CozinhaNotFoundException(dto.getCozinha().getId()));

        restaurante.setCozinha(cozinha);

        Cidade cidade = cidadeRepository.findById(dto.getEndereco().getCidade().getId())
                .orElseThrow(() -> new CidadeNotFoundException(dto.getEndereco().getCidade().getId()));

        restaurante.getEndereco().setCidade(cidade);

        return restauranteDTOAssembler.toDTO(restauranteRepository.save(restaurante));
    }

    @Transactional
    public RestauranteDTO patch(Long id, Map<String, Object> campos) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));

        ObjectMapper mapper = new ObjectMapper();
        Restaurante restauranteAtualizado = mapper.convertValue(campos, Restaurante.class);

        campos.forEach((nomeCampo, valor) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomeCampo);
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteAtualizado);
            ReflectionUtils.setField(field, restaurante, novoValor);
        });

        corrigirRelacionamentos(restaurante);

        return restauranteDTOAssembler.toDTO(restauranteRepository.save(restaurante));
    }

    @Transactional
    public void delete(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
        restauranteRepository.delete(restaurante);
    }

    public void checarSeExisteNome(String nome, Long id) {
        restauranteRepository.findByNome(nome)
                .ifPresent(restaurante -> {
                    if (!restaurante.getId().equals(id)) {
                        throw new EntidadeJaExistente();
                    }
                });
    }

    public void corrigirRelacionamentos(Restaurante restaurante) {
        if (restaurante.getCozinha() != null) {
            Long cozinhaId = restaurante.getCozinha().getId();
            Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                    .orElseThrow(() -> new CozinhaNotFoundException(cozinhaId));
            restaurante.setCozinha(cozinha);
        }

        if (restaurante.getFormaPagamentos() != null) {
            List<Long> ids = restaurante.getFormaPagamentos()
                    .stream()
                    .map(FormaPagamento::getId)
                    .toList();

            List<FormaPagamento> formas = formaPagamentoRepository.findAllById(ids);

            if (formas.size() != ids.size()) {
                throw new BaseEntityNotFoundException("Uma ou mais formas de pagamento não existem");
            }

            restaurante.setFormaPagamentos(formas);
        }
    }
    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
        restaurante.setAtivo(true);
        restauranteRepository.save(restaurante);

    }
    @Transactional
    public void desativar(Long id) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
        restaurante.setAtivo(false);
        restauranteRepository.save(restaurante);

    }

    @Transactional
    public void removerFormaPagamento(Long restauranteId, Long formaPagamentoId) {

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));

        FormaPagamento formaPagamento = formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(formaPagamentoId));

        restaurante.getFormaPagamentos().remove(formaPagamento);

    }
    @Transactional
    public void adicionarFormaPagamento(Long restauranteId, Long formaPagamentoId) {

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));

        FormaPagamento formaPagamento = formaPagamentoRepository.findById(formaPagamentoId)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(formaPagamentoId));

        if(restaurante.getFormaPagamentos().contains(formaPagamento)) {
            throw new FormaPagamentoJaExistente();
        }
        restaurante.getFormaPagamentos().add(formaPagamento);
    }
    public List<String> listarFormaPagamentos(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));


        return restaurante.getFormaPagamentos()
                .stream()
                .map(f -> f.getDescricao())
                .toList();

    }
    public List<ProdutoDTO> listarProdutos(Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));

        return restaurante.getProdutos()
                .stream()
                .map(produtoDTOAssembler::toDTO)
                .toList();

    }
    public void adicionarProduto(Long restauranteId, Long produtoId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(produtoId));

        if(restaurante.getProdutos().contains(produto)) {
            throw new FormaPagamentoJaExistente();
        }
        restaurante.getProdutos().add(produto);

    }
    public void removerProduto(Long restauranteId, Long produtoId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(restauranteId));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new FormaPagamentoNotFoundException(produtoId));


        restaurante.getProdutos().remove(produto);

    }

}