package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Funcao;
import com.algaworks.algafood.domain.model.UsuarioRestaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UsuarioRestauranteRepository extends JpaRepository<UsuarioRestaurante, Long> {

    boolean existsByUsuarioIdAndRestauranteIdAndFuncaoIn(Long usuarioId, Long restauranteId, Collection<Funcao> funcao);

    boolean existsByUsuarioIdAndRestauranteId(Long usuarioId, Long restauranteId);

    void deleteByUsuarioIdAndRestauranteId(Long usuarioId, Long restauranteId);

    Optional<UsuarioRestaurante> findByUsuarioIdAndRestauranteId(Long usuarioId, Long restauranteId);
}
