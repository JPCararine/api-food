package com.algaworks.algafood.core.validation;

import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.context.annotation.Bean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ImageValidator {

    public static void validar(InputStream inputStream) {
        try {
            BufferedImage imagem = ImageIO.read(inputStream);

            if(imagem == null) {
                throw new NegocioException("Arquivo não é uma imagem válida");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar imagem", e);
        }
    }
}
