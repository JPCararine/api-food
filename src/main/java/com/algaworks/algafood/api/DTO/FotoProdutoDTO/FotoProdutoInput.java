package com.algaworks.algafood.api.DTO.FotoProdutoDTO;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FotoProdutoInput {

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    private MultipartFile arquivo;
    @NotBlank
    private String descricao;
}
