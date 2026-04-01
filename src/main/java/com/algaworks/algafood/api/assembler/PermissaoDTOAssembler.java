package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Permissao.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoDTOAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public PermissaoDTO toDTO(Permissao permissao) {
        return  modelMapper.map(permissao, PermissaoDTO.class);
    }
}
