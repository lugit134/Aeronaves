package cl.GestionDrones.v1.aeronaves.exception;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler modernizado con Problem Details API (RFC 7807) 
 * Estándar Spring Boot 3.x para el microservicio de Aeronaves de la DGAC.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
        System.out.println("✅ GlobalExceptionHandler de Aeronaves SE HA REGISTRADO CORRECTAMENTE");
    }

    /**
     * NUEVO: Maneja casos donde el seguro de la aeronave no cumple con las normativas de la DGAC
     */
    @ExceptionHandler(SeguroInvalidoException.class)
    public ProblemDetail handleSeguroInvalido(SeguroInvalidoException ex) {
        System.out.println("🔴 GlobalExceptionHandler EJECUTADO - Violación de seguro aeronáutico: " + ex.getMessage());

        // Usamos UNPROCESSABLE_ENTITY (422) porque el JSON es válido, pero viola una regla de negocio
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_ENTITY, 
                ex.getMessage()
        );

        problem.setTitle("Seguro Aeronáutico Inválido o Vencido");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("codigo_error", "DGAC-ERR-SEGURO");
        
        return problem;
    }

    /**
     * Maneja errores de validación Jakarta (como fechas de seguros o patentes vacías) con Problem Details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException ex) {
        System.out.println("🔴 GlobalExceptionHandler EJECUTADO - Errores de validación en Aeronave detectados");

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Error de validación en los datos de la aeronave enviados");

        problem.setTitle("Aeronave Validation Error");
        problem.setProperty("timestamp", Instant.now());

        // Extraer errores con streams modernos
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage()
                                : "Valor inválido"));

        problem.setProperty("errors", errors);

        System.out.println("🔴 Errores encontrados en la aeronave: " + errors);
        return problem;
    }

    /**
     * Maneja errores de parseo de JSON (antes de validaciones)
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleJsonParseError(HttpMessageNotReadableException ex) {
        System.out.println("🟡 Error de parseo JSON capturado en Aeronaves");
        System.out.println("🟡 Mensaje: " + ex.getMessage());

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Error al procesar el JSON enviado para la aeronave");

        problem.setTitle("JSON Parse Error");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("detalle", ex.getMostSpecificCause().getMessage());
        return problem;
    }

    /**
     * Maneja aeronaves no encontradas con Problem Details
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        ProblemDetail problem =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());

        problem.setTitle("Aeronave Not Found");
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    /**
     * Maneja errores generales del servidor en el microservicio
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(Exception ex) {
        System.out.println("🔴 EXCEPCIÓN CAPTURADA EN AERONAVES: " + ex.getClass().getName());
        System.out.println("🔴 Mensaje: " + ex.getMessage());
        ex.printStackTrace();

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor en el microservicio de aeronaves");

        problem.setTitle("Internal Server Error");
        problem.setProperty("timestamp", Instant.now());
        problem.setProperty("detalle", ex.getMessage());
        problem.setProperty("tipoExcepcion", ex.getClass().getSimpleName());
        return problem;
    }
}
