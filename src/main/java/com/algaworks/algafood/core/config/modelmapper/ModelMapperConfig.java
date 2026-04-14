package com.algaworks.algafood.core.config.modelmapper;

import com.algaworks.algafood.api.v1.DTO.Restaurante.RestauranteDTO;
import com.algaworks.algafood.api.v1.DTO.Restaurante.RestauranteDetalhadoDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();


        modelMapper.typeMap(Restaurante.class, RestauranteDTO.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setFrete);

        modelMapper.typeMap(Restaurante.class, RestauranteDetalhadoDTO.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteDetalhadoDTO::setFrete);



        return modelMapper;
    }
}