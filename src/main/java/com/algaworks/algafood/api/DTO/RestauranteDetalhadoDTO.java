package com.algaworks.algafood.api.DTO;

import com.algaworks.algafood.domain.model.*;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class RestauranteDetalhadoDTO {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;


    private Boolean aberto;
    private Boolean ativo;

    private Cozinha cozinha;


    private List<Produto> produtos;


    private List<FormaPagamento> formaPagamento = new ArrayList<>();

    private Endereco endereco;


}
