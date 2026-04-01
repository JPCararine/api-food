package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Grupo.GrupoInputDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GrupoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toEntity(GrupoInputDTO grupoInputDTO) {
        return modelMapper.map(grupoInputDTO, Grupo.class);
    }
    public void copyToEntity(GrupoInputDTO grupoInputDTO, Grupo grupo) {
        grupo.setPermissoes(new ArrayList<>());
        modelMapper.map(grupoInputDTO, grupo);
    }
}
