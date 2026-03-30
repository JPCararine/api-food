package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CozinhaRepository extends JpaRepository<Cozinha,Long> {

    Optional<Cozinha> findByNome(String nome);

    List<Cozinha> findByNomeContainingIgnoreCase(String nome);


}
