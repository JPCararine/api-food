package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoAdminDTO;
import com.algaworks.algafood.api.DTO.Pedido.PedidoResumoDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTO;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTOPut;
import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDetalhadoDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.api.assembler.*;
import com.algaworks.algafood.domain.exception.JaExistente.EntidadeJaExistente;
import com.algaworks.algafood.domain.exception.JaExistente.FormaPagamentoJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.*;
import com.algaworks.algafood.domain.model.*;
import com.algaworks.algafood.infrastructure.repository.*;
import com.algaworks.algafood.infrastructure.repository.filter.PedidoFilter;
import com.algaworks.algafood.infrastructure.spec.PedidoSpecs;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;
    private final RestauranteDTOAssembler restauranteDTOAssembler;
    private final RestauranteInputDisassembler restauranteInputDisassembler;
    private final CidadeRepository cidadeRepository;


    public List<RestauranteDTO> listAll() {
        return restauranteRepository.findAll()
                .stream()
                .map(restauranteDTOAssembler::toDTO)
                .toList();
    }


    public RestauranteDTO findById(Long id) {
        Restaurante restaurante = buscarRestauranteOuFalhar(id);
        return restauranteDTOAssembler.toDTO(restaurante);
    }
    public RestauranteDetalhadoDTO findByIdDetalhado(Long id) {
         Restaurante restaurante = buscarRestauranteOuFalhar(id);
         return restauranteDTOAssembler.toDTODetalhado(restaurante);

    }
    public List<RestauranteDTO> listByAberto() {

        return restauranteRepository.findAbertosByRestaurante()
                .stream()
                .map(restauranteDTOAssembler::toDTO)
                .toList();

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

        Cozinha cozinha = buscarCozinhaOuFalhar(restauranteDTOPut.getCozinha().getId());

        Cidade cidade = buscarCidadeOuFalhar(restauranteDTOPut.getEndereco().getCidade().getId());

        Restaurante restaurante = restauranteInputDisassembler.toEntity(restauranteDTOPut);
        restaurante.setCozinha(cozinha);
        restaurante.getEndereco().setCidade(cidade);



        return restauranteDTOAssembler.toDTO(restauranteRepository.save(restaurante));
    }

    @Transactional
    public RestauranteDTO update(Long id, RestauranteDTOPut dto) {

        Restaurante restaurante = buscarRestauranteOuFalhar(id);

        checarSeExisteNome(dto.getNome(), restaurante.getId());

        restauranteInputDisassembler.copyToEntity(dto, restaurante);

        Cozinha cozinha = buscarCozinhaOuFalhar(dto.getCozinha().getId());

        restaurante.setCozinha(cozinha);

        Cidade cidade = buscarCidadeOuFalhar(dto.getEndereco().getCidade().getId());

        restaurante.getEndereco().setCidade(cidade);

        return restauranteDTOAssembler.toDTO(restauranteRepository.save(restaurante));
    }

    @Transactional
    public RestauranteDTO patch(Long id, Map<String, Object> campos) {
        Restaurante restaurante = buscarRestauranteOuFalhar(id);

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
        Restaurante restaurante = buscarRestauranteOuFalhar(id);
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
            Cozinha cozinha = buscarCozinhaOuFalhar(cozinhaId);
            restaurante.setCozinha(cozinha);
        }

    }
    @Transactional
    public void ativar(Long id) {
        Restaurante restaurante = buscarRestauranteOuFalhar(id);
        restaurante.setAtivo(true);
        restauranteRepository.save(restaurante);

    }
    @Transactional
    public void desativar(Long id) {
        Restaurante restaurante = buscarRestauranteOuFalhar(id);
        restaurante.setAtivo(false);
        restauranteRepository.save(restaurante);

    }

    @Transactional
    public void abrir(Long id) {
        Restaurante restaurante = buscarRestauranteOuFalhar(id);
        restaurante.setAberto(true);
        restauranteRepository.save(restaurante);
    }
    @Transactional
    public void fechar(Long id) {
        Restaurante restaurante = buscarRestauranteOuFalhar(id);
        restaurante.setAberto(false);
        restauranteRepository.save(restaurante);
    }

    @Transactional
    public void ativarVarios(List<Long> restauranteIds) {
        restauranteIds.forEach(this::ativar);
    }
    @Transactional
    public void desativarVarios(List<Long> restauranteIds) {
        restauranteIds.forEach(this::desativar);
    }

    @Transactional
    public void abrirVarios(List<Long> restauranteIds) {
        restauranteIds.forEach(this::abrir);
    }

    private Restaurante buscarRestauranteOuFalhar(Long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
    }
    private Cozinha buscarCozinhaOuFalhar(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNotFoundException(id));
    }
    private Cidade buscarCidadeOuFalhar(Long id) {
        return cidadeRepository.findById(id)
                .orElseThrow(() -> new CidadeNotFoundException(id));
    }

}