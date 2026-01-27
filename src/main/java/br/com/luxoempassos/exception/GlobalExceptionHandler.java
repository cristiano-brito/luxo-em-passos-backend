package br.com.luxoempassos.exception;

import br.com.luxoempassos.dto.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<ApiResponse<Object>> handleNegocio(NegocioException ex) {
        long inicio = System.currentTimeMillis();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                ApiResponse.erro(ex.getMessage(), inicio)
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(EntityNotFoundException ex) {
        long inicio = System.currentTimeMillis();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.erro("Recurso não encontrado: " + ex.getMessage(), inicio)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral(Exception ex) {
        long inicio = System.currentTimeMillis();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.erro("Ocorreu um erro inesperado no servidor.", inicio)
        );
    }

    @ExceptionHandler(org.springframework.web.bind.MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(org.springframework.web.bind.MethodArgumentNotValidException ex) {
        long inicio = System.currentTimeMillis();

        String mensagem = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.erro("Erro de validação: " + mensagem, inicio)
        );
    }
}
