package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.FotoProdutoDTO.FotoProdutoDTO;
import com.algaworks.algafood.api.DTO.Produto.FotoProdutoInput;
import com.algaworks.algafood.api.assembler.FotoProdutoDTOAssembler;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoAndRestauranteNotFoundException;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoNotFoundException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infrastructure.repository.ProdutoRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FotoProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;
    private final FotoProdutoDTOAssembler fotoProdutoDTOAssembler;

    public FotoProdutoDTO atualizarFoto(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput) {
        Produto produto = produtoRepository.findByRestauranteIdAndId(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoAndRestauranteNotFoundException(restauranteId, produtoId));
            FotoProduto foto = new FotoProduto();
            foto.setProduto(produto);
            foto.setDescricao(fotoProdutoInput.getDescricao());
            foto.setContentType(fotoProdutoInput.getArquivo().getContentType());
            foto.setTamanho(fotoProdutoInput.getArquivo().getSize());
            foto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());

            return fotoProdutoDTOAssembler.toDTO(salvar(foto));

    }
    public FotoProduto salvar(FotoProduto fotoProduto) {
        Long restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, fotoProduto.getProduto().getId());
        if(fotoExistente.isPresent()) {
            produtoRepository.delete(fotoExistente.get());
        }
        return produtoRepository.save(fotoProduto);

    }


}
