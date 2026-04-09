package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.DTO.FotoProdutoDTO.FotoProdutoDTO;
import com.algaworks.algafood.api.DTO.FotoProdutoDTO.FotoProdutoInput;
import com.algaworks.algafood.api.assembler.FotoProdutoDTOAssembler;
import com.algaworks.algafood.core.validation.ImageValidator;
import com.algaworks.algafood.domain.exception.NotFound.ProdutoAndRestauranteNotFoundException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.infrastructure.repository.ProdutoRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FotoProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FotoProdutoDTOAssembler fotoProdutoDTOAssembler;
    private final StorageService storageService;


    @Transactional
    public FotoProdutoDTO atualizarFoto(Long restauranteId, Long produtoId, FotoProdutoInput fotoProdutoInput) throws IOException {
        Produto produto = produtoRepository.findByRestauranteIdAndId(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoAndRestauranteNotFoundException(restauranteId, produtoId));
            FotoProduto foto = new FotoProduto();
            foto.setProduto(produto);
            foto.setDescricao(fotoProdutoInput.getDescricao());
            foto.setContentType(fotoProdutoInput.getArquivo().getContentType());
            foto.setTamanho(fotoProdutoInput.getArquivo().getSize());
            foto.setNomeArquivo(fotoProdutoInput.getArquivo().getOriginalFilename());
            InputStream inputStream = fotoProdutoInput.getArquivo().getInputStream();
            byte [] bytes = inputStream.readAllBytes();

            ImageValidator.validar(new ByteArrayInputStream(bytes));

            FotoProduto fotoSalva = salvar(foto, new ByteArrayInputStream(bytes));

            return fotoProdutoDTOAssembler.toDTO(fotoSalva);

    }
    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosArquivo) {
        Long restauranteId = fotoProduto.getProduto().getRestaurante().getId();
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, fotoProduto.getProduto().getId());
        String nomeNovoArquivo = storageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());

        String arquivoAntigo = null;

        if(fotoExistente.isPresent()) {
            arquivoAntigo = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }
        fotoProduto.setNomeArquivo(nomeNovoArquivo);
        fotoProduto = produtoRepository.save(fotoProduto);
        produtoRepository.flush();

        StorageService.NovaFoto novaFoto = StorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .contentType(fotoProduto.getContentType())
                .inputStream(dadosArquivo)
                .build();

        storageService.substituir(arquivoAntigo, novaFoto);

        return fotoProduto;


    }
    public StorageService.FotoRecuperada download(Long restauranteId,  Long produtoId)  {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        String nomeArquivo = fotoProduto.getNomeArquivo();

        StorageService.FotoRecuperada fotoRecuperada = storageService.recuperar(nomeArquivo);

        return fotoRecuperada;

    }
    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoAndRestauranteNotFoundException(restauranteId, produtoId));
    }
    @Transactional
    public void delete(Long restauranteId, Long produtoId) {
        FotoProduto fotoProduto = buscarOuFalhar(restauranteId, produtoId);
        produtoRepository.delete(fotoProduto);
        storageService.deletar(fotoProduto.getNomeArquivo());
    }


}
