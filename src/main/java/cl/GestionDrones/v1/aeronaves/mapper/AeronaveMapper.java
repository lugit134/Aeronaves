package cl.GestionDrones.v1.aeronaves.mapper;
import cl.GestionDrones.v1.aeronaves.dto.CreateAeronaveRequest;
import cl.GestionDrones.v1.aeronaves.dto.UpdateAeronaveRequest;
import cl.GestionDrones.v1.aeronaves.model.Aeronave;

public class AeronaveMapper {
    /**
     * Convierte CreateAeronaveRequest a Aeronave (para POST).
     * El ID se pasa como null temporalmente ya que la base de datos lo autogenerará.
     */
    public static Aeronave toModel(CreateAeronaveRequest request) {
        return new Aeronave(
                0, 
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
     * El ID se obtiene del path parameter de la URL.
     */
    public static Aeronave toModel(int id, UpdateAeronaveRequest request) {
        return new Aeronave(
                id, // ID proveniente del path parameter
                request.patente(),
                request.numeroSerie(),
                request.marca(),
                request.modelo(),
                request.estado(),
                request.fechaVencimientoSeguro()
        );
    }

}
