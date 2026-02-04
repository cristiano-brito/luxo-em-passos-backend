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

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDataIntegrity(org.springframework.dao.DataIntegrityViolationException ex) {
        long inicio = System.currentTimeMillis();
        String mensagem = "Erro de integridade: Este registro (CPF ou E-mail) já existe no sistema.";

        // Opcional: Refinar a mensagem verificando o nome da constraint
        if (ex.getMessage() != null && ex.getMessage().contains("uk_cliente_cpf_tenant")) {
            mensagem = "Este CPF já está cadastrado para esta boutique.";
        } else if (ex.getMessage() != null && ex.getMessage().contains("uk_cliente_email_tenant")) {
            mensagem = "Este e-mail já está cadastrado para esta boutique.";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ApiResponse.erro(mensagem, inicio)
        );
    }
}
