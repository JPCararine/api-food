package com.algaworks.algafoodauth.repository;

import com.algaworks.algafoodauth.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u left join fetch u.grupo where u.email = :email")
    Usuario findByEmail(String email);
}
