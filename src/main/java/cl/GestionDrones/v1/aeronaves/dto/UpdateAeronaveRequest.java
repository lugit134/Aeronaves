package cl.GestionDrones.v1.aeronaves.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

/**
 * DTO para actualizar una aeronave existente (PUT).
 * No incluye ID porque se obtiene del path parameter en la URL del endpoint.
 */
public record UpdateAeronaveRequest(
        @NotBlank(message = "La patente no puede ser vacía") 
        String patente,

        @NotBlank(message = "El número de serie no puede ser vacío") 
        String numeroSerie,

        @NotBlank(message = "La marca no puede ser vacía") 
        String marca,

        @NotBlank(message = "El modelo no puede ser vacío") 
        String modelo,

        @NotBlank(message = "El estado no puede ser vacío") 
        String estado, // Ejemplo: "ACTIVO", "EN_MANTENIMIENTO", "RETIRO"

        @NotNull(message = "La fecha de vencimiento del seguro es obligatoria")
        @Future(message = "El seguro ya ha expirado ")
        @FutureOrPresent(message = "El seguro contratado debe estar vigente (fecha actual o futura)")
        LocalDate fechaVencimientoSeguro
) {}
