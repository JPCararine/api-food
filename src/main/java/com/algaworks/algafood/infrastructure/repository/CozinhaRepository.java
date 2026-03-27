package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CozinhaRepository extends JpaRepository<Cozinha,Long> {

    List<Cozinha> findByNome(String nome);

    List<Cozinha> findByNomeContainingIgnoreCase(String nome);


}
