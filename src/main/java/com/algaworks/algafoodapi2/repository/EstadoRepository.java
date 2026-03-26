package com.algaworks.algafoodapi2.repository;

import com.algaworks.algafoodapi2.domain.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstadoRepository extends JpaRepository<Estado, Long> {

    boolean existsByNome(String nome);

    Optional<Estado> findByNome(String nome);
}
