package cl.GestionDrones.v1.aeronaves.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

/**
 * DTO para registrar una nueva aeronave (POST).
 * No incluye ID porque se genera automáticamente en la base de datos.
 */
public record CreateAeronaveRequest(
        @NotBlank(message = "La patente no puede ser vacía") 
        String patente,

        @NotBlank(message = "El número de serie no puede ser vacío") 
        String numeroSerie,

        @NotBlank(message = "La marca no puede ser vacía") 
        String marca,

        @NotBlank(message = "El modelo no puede ser vacío") 
        String modelo,

        @NotBlank(message = "El estado inicial no puede ser vacío") 
        String estado, // Ejemplo: "ACTIVO"

        @NotNull(message = "La fecha de vencimiento del seguro es obligatoria")
        @Future(message = "El seguro ya ha expirado ")
        @FutureOrPresent(message = "El seguro contratado debe estar vigente (fecha actual o futura)")
        LocalDate fechaVencimientoSeguro
) {}