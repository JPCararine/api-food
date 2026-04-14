package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.DTO.Permissao.UsuarioInputDTO;
import com.algaworks.algafood.domain.model.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoInputDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Permissao toEntity(UsuarioInputDTO permissaoInputDTO) {
        return modelMapper.map(permissaoInputDTO, Permissao.class);
    }
    public void copyToEntity(UsuarioInputDTO permissaoInputDTO, Permissao permissao) {
        modelMapper.map(permissaoInputDTO, permissao);
    }
}
