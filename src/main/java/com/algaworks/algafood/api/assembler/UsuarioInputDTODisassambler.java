package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Usuario.UsuarioDTO;
import com.algaworks.algafood.api.DTO.Usuario.UsuarioInputDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class UsuarioInputDTODisassambler {
    @Autowired
    private ModelMapper modelMapper;

    public Usuario toEntity(UsuarioInputDTO usuarioInputDTO) {
        return modelMapper.map(usuarioInputDTO, Usuario.class);
    }
    public void copyToEntity(UsuarioInputDTO usuarioInputDTO, Usuario usuario) {
        usuario.setGrupos(new ArrayList<>());
        modelMapper.map(usuarioInputDTO, usuario);
    }
}
