package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Usuario.UsuarioDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdNomeEmailDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO toDTO(Usuario usuario) {

        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    public UsuarioIdNomeEmailDTO toDTOIdNome(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioIdNomeEmailDTO.class);
    }
}
