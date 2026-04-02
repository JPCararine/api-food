package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Usuario.UsuarioDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioIdNomeDTO;
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
    public UsuarioIdNomeDTO toDTOIdNome(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioIdNomeDTO.class);
    }
}
