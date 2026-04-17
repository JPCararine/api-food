package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.DTO.UsuarioRestaurante.UsuarioRestauranteResumoDTO;
import com.algaworks.algafood.domain.model.UsuarioRestaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRestauranteDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioRestauranteResumoDTO toUsuarioResumoDTO(UsuarioRestaurante usuarioRestaurante) {
        return modelMapper.map(usuarioRestaurante, UsuarioRestauranteResumoDTO.class);
    }
}
