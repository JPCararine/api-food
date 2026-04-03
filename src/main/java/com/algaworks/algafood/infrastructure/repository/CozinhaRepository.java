package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CozinhaRepository extends JpaRepository<Cozinha,Long> {

    Page<Cozinha> findByNomeContaining(String nome, Pageable pageable);

    List<Cozinha> findByNomeContainingIgnoreCase(String nome);

    Optional<Cozinha> findByNome(String nome);


}
