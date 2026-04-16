package com.algaworks.algafoodauth.core;

import com.algaworks.algafoodauth.domain.Permissao;
import com.algaworks.algafoodauth.domain.Usuario;
import com.algaworks.algafoodauth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;



    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o email: " + username);
        }

        usuario.getGrupo().forEach(grupo -> {
                            grupo.getPermissoes().size();
                        });

        System.out.println("Usuario encontrado com o email: " + username);
        System.out.println(usuario.getGrupo());


        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getGrupo().stream()
                        .flatMap(grupo -> grupo.getPermissoes().stream())
                        .map(permissao -> new SimpleGrantedAuthority(permissao.getNome()))
                        .toList()


        );
    }

}
