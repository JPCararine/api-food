package com.algaworks.algafood.api.v1.assembler;

import com.algaworks.algafood.api.v1.DTO.Restaurante.RestauranteDTOPut;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RestauranteInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toEntity(RestauranteDTOPut restauranteDTOPut) {
        return modelMapper.map(restauranteDTOPut, Restaurante.class);



    }
    public void copyToEntity(RestauranteDTOPut restauranteDTOPut, Restaurante restaurante) {
        restaurante.setCozinha(new Cozinha());

        if(restaurante.getEndereco() != null) {
            restaurante.getEndereco().setCidade(new Cidade());
        }
        restaurante.setFormaPagamentos(new ArrayList<>());
        modelMapper.map(restauranteDTOPut, restaurante);
    }
}
