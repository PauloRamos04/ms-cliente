package saudeconectada.fatec.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        logger.error("IllegalStateException: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de estado ilegal",
                "IllegalStateException",
                "ILLEGAL_STATE",
                Map.of("error", "Operação inválida.")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de argumento inválido",
                "IllegalArgumentException",
                "INVALID_ARGUMENT",
                Map.of("error", "Argumento fornecido não é válido.")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        logger.error("ConstraintViolationException: {}", ex.getMessage(), ex);
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(violation -> violation.getPropertyPath().toString(), violation -> "O campo " + violation.getPropertyPath() + " é obrigatório."));
        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de validação",
                "ConstraintViolationException",
                "VALIDATION_ERROR",
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((FieldError error) -> {
            String fieldName = error.getField();
            String errorMessage = "O campo " + fieldName + " não pode ser nulo.";
            errors.put(fieldName, errorMessage);
        });
        logger.error("Erro na validação: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de validação nos campos",
                "MethodArgumentNotValidException",
                "FIELD_VALIDATION_ERROR",
                errors
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Exception: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Ocorreu um erro inesperado",
                "Exception",
                "INTERNAL_SERVER_ERROR",
                Map.of("error", "Erro interno no servidor.")
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidFormatException(HttpMessageNotReadableException ex) {
        String errorMessage = "Erro ao ler o valor enviado. Verifique se todos os campos estão corretos.";
        logger.error("Erro de leitura de mensagem: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de leitura de campo.",
                "InvalidFormatException",
                "INVALID_FORMAT",
                Map.of("error", "Campo com valor inválido ou ausente. Verifique os dados enviados.")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        logger.error("CustomException: {}", ex.getErrorMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de cadastro",
                "CustomException",
                ex.getErrorCode() != null ? ex.getErrorCode() : "CUSTOM_ERROR",
                Map.of("error", ex.getErrorMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}
