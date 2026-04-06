package com.algaworks.algafood.core.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileContentValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes;
    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
    }
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return file == null || this.allowedContentTypes.contains(file.getContentType());
    }
}
