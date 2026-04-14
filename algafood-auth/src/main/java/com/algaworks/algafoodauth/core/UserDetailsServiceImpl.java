package com.algaworks.algafoodauth.core;

import com.algaworks.algafoodauth.domain.Usuario;
import com.algaworks.algafoodauth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
        }


        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getGrupo().stream()
                        .map(grupo -> new SimpleGrantedAuthority(grupo.getNome()))
                        .toList()
        );
    }

}
