package com.algaworks.algafood.core.validation;

import org.springframework.context.annotation.Bean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ImageValidator {

    public static void validar(InputStream inputStream) {
        try {
            BufferedImage imagem = ImageIO.read(inputStream);

            if(imagem == null) {
                throw new RuntimeException("Arquivo não é uma imagem válida");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao validar imagem", e);
        }
    }
}
