package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.Produto.ProdutoDTO;
import com.algaworks.algafood.api.DTO.Produto.ProdutoInputDTO;
import com.algaworks.algafood.api.assembler.ProdutoDTOAssembler;
import com.algaworks.algafood.api.assembler.ProdutoInputDTODisassembler;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoAndRestauranteNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.RestauranteNotFoundException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.infrastructure.repository.ProdutoRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import tools.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoDTOAssembler produtoDTOAssembler;
    private final ProdutoInputDTODisassembler produtoInputDTODisassembler;


    public List<ProdutoDTO> findAll() {

        return produtoRepository.findAll()
                .stream()
                .map(produtoDTOAssembler::toDTO)
                .toList();
    }
    public List<ProdutoDTO> find(String nome, BigDecimal precoInicial, BigDecimal precoFinal) {
        return produtoRepository.find(nome, precoInicial, precoFinal)
                .stream()
                .map(produtoDTOAssembler::toDTO)
                .toList();
    }
    public ProdutoDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        return produtoDTOAssembler.toDTO(produto);
    }
    @Transactional
    public ProdutoDTO save(ProdutoInputDTO produtoInputDTO) {

        Produto produto = produtoInputDTODisassembler.toEntity(produtoInputDTO);

        return produtoDTOAssembler.toDTO(produtoRepository.save(produto));

    }

    public void delete(long id){
        Produto produto = produtoRepository.findById(id)
                        .orElseThrow(() -> new ProdutoNotFoundException(id));
        produtoRepository.delete(produto);
    }
    @Transactional
    public ProdutoDTO merge(Map<String, Object> camposPassados, Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));
        ObjectMapper mapper = new ObjectMapper();
        Produto produtoAtual = mapper.convertValue(camposPassados, Produto.class);

        List<String> camposIgnorados = List.of("id");

        camposPassados.forEach((key, value) -> {
            if(camposIgnorados.contains(key)) {
                return;
            }
            Field campo = ReflectionUtils.findField(Produto.class, key);
            campo.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(campo, produtoAtual);

            ReflectionUtils.setField(campo, produto, novoValor);
        });
        return produtoDTOAssembler.toDTO(produtoRepository.save(produto));
    }
    public ProdutoDTO findByRestaurante(Long restauranteId, Long produtoId) {
        Produto produto = produtoRepository.findByRestauranteIdAndId(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoAndRestauranteNotFoundException(restauranteId, produtoId));

        if(!produto.getRestaurante().getId().equals(restauranteId)) {
            throw new RuntimeException("Produto não pertence ao restaurante");
        }
        return produtoDTOAssembler.toDTO(produto);

    }

}
