package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Permissao.PermissaoDTO;
import com.algaworks.algafood.api.DTO.Permissao.PermissaoInputDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Permissao toEntity(PermissaoInputDTO permissaoInputDTO) {
        return modelMapper.map(permissaoInputDTO, Permissao.class);
    }
    public void copyToEntity(PermissaoInputDTO permissaoInputDTO, Permissao permissao) {
        modelMapper.map(permissaoInputDTO, permissao);
    }
}
