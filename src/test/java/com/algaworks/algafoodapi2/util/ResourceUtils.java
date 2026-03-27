package com.algaworks.algafoodapi2.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
public class ResourceUtils {

    public static String StringFromRecourse(String nome) {
        try {
            InputStream stream = ResourceUtils.class.getResourceAsStream(nome);
            return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
        } catch(IOException e)
            {
            throw new RuntimeException(e);
            }
    }
}
