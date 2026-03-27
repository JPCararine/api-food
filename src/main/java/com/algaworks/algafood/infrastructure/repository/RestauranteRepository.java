package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> consultarPorNome(String nome, Long id);
    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long id);

    @Query("select r from Restaurante r join r.cozinha left join fetch r.formaPagamento")
    List<Restaurante> findAll();

    boolean existsByCozinhaId(Long cozinhaId);

    Optional<Restaurante> findByNome(String nome);


}
