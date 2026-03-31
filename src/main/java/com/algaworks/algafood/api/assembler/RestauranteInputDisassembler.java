package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.DTO.Restaurante.RestauranteDTOPut;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteInputDisassembler {
    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toEntity(RestauranteDTOPut restauranteDTOPut, Cozinha cozinha) {
        return modelMapper.map(restauranteDTOPut, Restaurante.class);
    }
    public void copyToEntity(RestauranteDTOPut restauranteDTOPut, Restaurante restaurante) {
        restaurante.setCozinha(null);
        modelMapper.map(restauranteDTOPut, restaurante);
    }
}
