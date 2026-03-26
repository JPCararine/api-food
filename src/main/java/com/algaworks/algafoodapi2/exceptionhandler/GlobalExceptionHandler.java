package com.algaworks.algafoodapi2.exceptionhandler;

import com.algaworks.algafoodapi2.domain.exception.EmUso.EntidadeEmUsoException;
import com.algaworks.algafoodapi2.domain.exception.NotFound.EntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.util.InvalidUrlException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.exc.InvalidFormatException;
import tools.jackson.databind.exc.PropertyBindingException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        Throwable exception = ExceptionUtils.getRootCause(ex);
        if (exception instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) exception, headers, status, request);
        }
        if (exception instanceof PropertyBindingException) {
            return handleIgnorePropertyException((PropertyBindingException) exception, headers, status, request);
        }
        if(exception instanceof MethodArgumentNotValidException) {
            return handleMethodArgumentNotValid((MethodArgumentNotValidException) exception, headers, status, request);
        }
        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe";
        Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);


    }
    protected ResponseEntity<Object>  handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                 HttpStatusCode status, WebRequest request) {
        List<String> paths = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    String path = error.getField();
                    String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
                    return String.format("'%s': '%s'", path, message);
                        })
                .collect(Collectors.toList());
        String detail = String.format("Campos inválidos: '%s' ", String.join(", ", paths));
        Problem problem = createProblemBuilder((HttpStatus) status, ProblemType.ATRIBUTO_FALTANDO, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest request) {

        String path = pather(ex.getPath());
        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s', recebeu o valor '%s', " + " que é de um tipo inválido." +
                " Corrija e informe um valor compatível com o tipo %s.",path ,ex.getValue(), ex.getTargetType().getSimpleName());
        Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }
    private ResponseEntity<Object> handleIgnorePropertyException(PropertyBindingException ex, HttpHeaders headers,
                                                                 HttpStatusCode status, WebRequest request) {
        String path = pather(ex.getPath());
        String detail = String.format("Campo '%s' inválido", path);
        ProblemType problemType = ProblemType.PROPRIEDADE_IGNORADA;
        Problem problem = createProblemBuilder((HttpStatus) status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleNotFound(EntityNotFoundException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
        Problem problem = createProblemBuilder(status, problemType, ex.getMessage()).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        String detail = ex.getMessage();
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        if(body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if(body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }


    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);

    }
    private String pather(List<JacksonException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getPropertyName())
                .collect(Collectors.joining("."));
    }
}
