package com.algaworks.algafoodapi2.repository;

import com.algaworks.algafoodapi2.domain.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

    boolean existsByEstadoId(Long id);

}
