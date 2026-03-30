package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.RestauranteDTO;
import com.algaworks.algafood.api.DTO.RestauranteDTOPut;
import com.algaworks.algafood.api.DTO.RestauranteDetalhadoDTO;
import com.algaworks.algafood.api.assembler.RestauranteDTOAssembler;
import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.domain.exception.JaExistente.EntidadeJaExistente;
import com.algaworks.algafood.domain.exception.NotFound.BaseEntityNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.CozinhaNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.CozinhaRepository;
import com.algaworks.algafood.infrastructure.repository.FormaPagamentoRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
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

    public List<RestauranteDTO> consultarPorNome(String nome, Long id) {
        if (nome == null && id == null) {
            return listAll();
        }
        return restauranteRepository.consultarPorNome(nome, id)
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
    public RestauranteDTO save(RestauranteDTOPut dto) {
        checarSeExisteNome(dto.getNome(), dto.getId());

        Cozinha cozinha = cozinhaRepository.findById(dto.getCozinha().getId())
                .orElseThrow(() -> new CozinhaNotFoundException(dto.getCozinha().getId()));

        Restaurante restaurante = restauranteInputDisassembler.toEntity(dto, cozinha);

        return restauranteDTOAssembler.toDTO(restauranteRepository.save(restaurante));
    }

    @Transactional
    public RestauranteDTO replace(Long id, RestauranteDTOPut dto) {
        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));

        checarSeExisteNome(dto.getNome(), id);

        restaurante.setNome(dto.getNome());
        restaurante.setTaxaFrete(dto.getTaxaFrete());

        Cozinha cozinha = cozinhaRepository.findById(dto.getCozinha().getId())
                .orElseThrow(() -> new CozinhaNotFoundException(dto.getCozinha().getId()));

        restaurante.setCozinha(cozinha);

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

        if (restaurante.getFormaPagamento() != null) {
            List<Long> ids = restaurante.getFormaPagamento()
                    .stream()
                    .map(FormaPagamento::getId)
                    .toList();

            List<FormaPagamento> formas = formaPagamentoRepository.findAllById(ids);

            if (formas.size() != ids.size()) {
                throw new BaseEntityNotFoundException("Uma ou mais formas de pagamento não existem");
            }

            restaurante.setFormaPagamento(formas);
        }
    }
}