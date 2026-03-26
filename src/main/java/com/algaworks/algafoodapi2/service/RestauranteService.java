package com.algaworks.algafoodapi2.service;

import com.algaworks.algafoodapi2.domain.exception.NotFound.CozinhaNotFoundException;
import com.algaworks.algafoodapi2.domain.exception.NotFound.EntityNotFoundException;
import com.algaworks.algafoodapi2.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafoodapi2.domain.model.*;
import com.algaworks.algafoodapi2.infrastructure.repository.spec.RestauranteSpecs;
import com.algaworks.algafoodapi2.repository.CozinhaRepository;
import com.algaworks.algafoodapi2.repository.FormaPagamentoRepository;
import com.algaworks.algafoodapi2.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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


    public List<Restaurante> listAll() {
        return restauranteRepository.findAll();
    }


    public Restaurante findById(long id) {
        return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));
    }

    public List<Restaurante> consultarPorNome(String nome, Long id) {
        if (nome == null && id == null) {
            return restauranteRepository.findAll();
        }
        return restauranteRepository.consultarPorNome(nome, id);
    }

    public Restaurante save(Restaurante restauranteRequest) {
        Cozinha cozinha = cozinhaRepository.findById(restauranteRequest.getCozinha().getId())
                .orElseThrow(() -> new CozinhaNotFoundException(restauranteRequest.getCozinha().getId()));
        List<Long> ids = restauranteRequest.getFormaPagamento().stream()
                .map(formaPagamento -> formaPagamento.getId())
                .toList();
        List<FormaPagamento> formas = formaPagamentoRepository.findAllById(ids);
        Restaurante restaurante = Restaurante.builder()
                .nome(restauranteRequest.getNome())
                .taxaFrete(restauranteRequest.getTaxaFrete())
                .cozinha(cozinha)
                .formaPagamento(formas)
                .aberto(false)
                .ativo(true)
                .build();
        return restauranteRepository.save(restaurante);


    }

    public void delete(long id) {
        restauranteRepository.delete(findById(id));
    }

    public Restaurante replace(Long id, Restaurante restauranteRequest) {

        Restaurante restaurante = restauranteRepository.findById(id)
                .orElseThrow(() -> new RestauranteNotFoundException(id));

        BeanUtils.copyProperties(restauranteRequest, restaurante,
                "id", "formaPagamentos", "endereco", "dataCadastro", "aberto", "ativo");

        Long cozinhaId = restauranteRequest.getCozinha().getId();

        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                        .orElseThrow(() -> new CozinhaNotFoundException(cozinhaId));
        restaurante.setCozinha(cozinha);

        corrigirRelacionamentos(restaurante);
        return restauranteRepository.save(restaurante);

        }



    public void merge(Map<String, Object> camposPassados, Restaurante restauranteAtual) {

        ObjectMapper mapper = new ObjectMapper();
        Restaurante restaurante = mapper.convertValue(camposPassados, Restaurante.class);

        List<String> camposIgnorados = List.of("id");

        camposPassados.forEach((key, value) -> {
            if(camposIgnorados.contains(key)) {
                return;
            }
            Field campo = ReflectionUtils.findField(Restaurante.class, key);
            campo.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(campo, restaurante);

            ReflectionUtils.setField(campo, restauranteAtual, novoValor);
        });

    }
    public Restaurante update(Restaurante restauranteAtual) {
        return restauranteRepository.save(restauranteAtual);
    }

    public List<Restaurante> find(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return restauranteRepository.find(nome, taxaInicial, taxaFinal);
    }

    //    public List<Restaurante> findTaxaGratis(String nome, BigDecimal taxaFrete) {
//        List<Restaurante> restaurantes = restauranteRepository.findFreteGratis(nome, taxaFrete);
//        if(restaurantes.isEmpty()) {
//            throw new RuntimeException("Nenhum restaurante encontrado");
//        }
//        return restaurantes.stream()
//                .filter(r -> r.getTaxaFrete().equals(BigDecimal.ZERO))
//                .toList();
//
//    }
    public List<Restaurante> findTaxaGratis(String nome) {
        Specification<Restaurante> spec = Specification.where(RestauranteSpecs.comFreteGratis());

        if (nome != null) {
            spec = spec.and(RestauranteSpecs.comNome(nome));

        }

        List<Restaurante> restaurantes = restauranteRepository.findAll(spec);

        if(restaurantes.isEmpty()) {
            throw new EntityNotFoundException("Nenhum restaurante encontrado");
        }
        return restauranteRepository.findAll(spec);
    }
        public Optional<Restaurante> primeiroRestaurante() {
        return restauranteRepository.acharPrimeiro();
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
                throw new EntityNotFoundException("Uma ou mais formas de pagamento não existem");
            }

            restaurante.setFormaPagamento(formas);
        }
    }
    }





