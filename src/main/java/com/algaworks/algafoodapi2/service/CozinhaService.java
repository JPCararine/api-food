package com.algaworks.algafoodapi2.service;

import com.algaworks.algafoodapi2.domain.exception.EmUso.CozinhaEmUsoException;
import com.algaworks.algafoodapi2.domain.exception.NotFound.CozinhaNotFoundException;
import com.algaworks.algafoodapi2.domain.exception.EmUso.EntidadeEmUsoException;
import com.algaworks.algafoodapi2.domain.model.Cozinha;
import com.algaworks.algafoodapi2.repository.CozinhaRepository;
import com.algaworks.algafoodapi2.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CozinhaService {

    private final CozinhaRepository cozinhaRepository;
    private final RestauranteRepository restauranteRepository;


    public List<Cozinha> findAll() {
        return cozinhaRepository.findAll();
    }

    public List<Cozinha> findbyNome(String nome) {
        if(nome == null) {
            return cozinhaRepository.findAll();
        }
        return cozinhaRepository.findByNome(nome);
    }
    public List<Cozinha> findByNomeContaningIgnoreCase(String nome) {
        return cozinhaRepository.findByNomeContainingIgnoreCase(nome);
    }
    public Cozinha findById(Long id) {
        return cozinhaRepository.findById(id)
                .orElseThrow(() -> new CozinhaNotFoundException(id));
    }
    @Transactional
    public Cozinha save(Cozinha cozinhaRequest) {
        Cozinha cozinha = Cozinha.builder()
                .nome(cozinhaRequest.getNome())
                .build();

        return cozinhaRepository.save(cozinha);
    }
    @Transactional
    public void delete(long id) {

        if(restauranteRepository.existsByCozinhaId(id)) {
            throw new CozinhaEmUsoException(id);
        }
        cozinhaRepository.delete(findById(id));

    }
    @Transactional
    public Cozinha replace(Long id, Cozinha cozinhaRequest) {
        Cozinha cozinha = findById(id);
        cozinha.setNome(cozinhaRequest.getNome());
        cozinha.setId(cozinhaRequest.getId());
        return cozinhaRepository.save(cozinha);
    }
}
