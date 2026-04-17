package com.algaworks.algafood.core.config.modelmapper;

import com.algaworks.algafood.api.v1.DTO.Pedido.PedidoInputDTO;
import com.algaworks.algafood.api.v1.DTO.Restaurante.RestauranteDTO;
import com.algaworks.algafood.api.v1.DTO.Restaurante.RestauranteDetalhadoDTO;
import com.algaworks.algafood.domain.model.Pedido;
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

        modelMapper.typeMap(PedidoInputDTO.class, Pedido.class)
                .addMappings(mapper -> {
                    mapper.skip(Pedido::setItens);
                    mapper.skip(Pedido::setId);
                });



        return modelMapper;
    }
}