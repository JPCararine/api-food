package com.algaworks.algafoodapi2.repository;

import com.algaworks.algafoodapi2.domain.model.Cozinha;
import com.algaworks.algafoodapi2.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CozinhaRepository extends JpaRepository<Cozinha,Long> {

    List<Cozinha> findByNome(String nome);

    List<Cozinha> findByNomeContainingIgnoreCase(String nome);


}
