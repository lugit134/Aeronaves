package cl.GestionDrones.v1.aeronaves.mapper;

import cl.GestionDrones.v1.aeronaves.dto.CreateAeronaveRequest;
import cl.GestionDrones.v1.aeronaves.dto.UpdateAeronaveRequest;
import cl.GestionDrones.v1.aeronaves.model.Aeronave;

public class AeronaveMapper {

    /**
     * Convierte CreateAeronaveRequest a Aeronave (para POST).
     * El ID se pasa como null ya que la base de datos lo autogenerará.
     */
    public static Aeronave toModel(CreateAeronaveRequest request) {
        return new Aeronave(
                null, // ID Long nulo para que JPA lo autogenere
                request.idEmpresaProveedora(), // Requerido por la relación lógica
                request.patente(),
                request.numeroSerie(),
                request.marca(),
                request.modelo(),
                request.estado(),
                request.fechaVencimientoSeguro()
        );
    }

    /**
     * Convierte UpdateAeronaveRequest a Aeronave (para PUT).
     * El ID se obtiene del path parameter de la URL como Long.
     */
    public static Aeronave toModel(Long id, UpdateAeronaveRequest request) {
        return new Aeronave(
                id, // ID Long proveniente del path parameter
                request.idEmpresaProveedora(), // Requerido por la relación lógica
                request.patente(),
                request.numeroSerie(),
                request.marca(),
                request.modelo(),
                request.estado(),
                request.fechaVencimientoSeguro()
        );
    }
}
