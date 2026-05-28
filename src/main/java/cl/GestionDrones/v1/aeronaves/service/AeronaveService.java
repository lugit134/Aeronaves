package cl.GestionDrones.v1.aeronaves.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.GestionDrones.v1.aeronaves.model.Aeronave;
import cl.GestionDrones.v1.aeronaves.repository.AeronaveRepository;
// Nota: Si creas una excepción personalizada para aeronaves, impórtala aquí. 
// Por ahora usaré una genérica de Spring o Runtime para que compile de inmediato.
import jakarta.persistence.EntityNotFoundException;

@Service
public class AeronaveService {

    @Autowired
    private AeronaveRepository aeronaveRepository;

    public List<Aeronave> getAeronaves() {
        return aeronaveRepository.findAll();
    }

    public Aeronave saveAeronave(Aeronave aeronave) {
        return aeronaveRepository.save(aeronave);
    }

    // CORREGIDO: Cambiado 'int id' a 'Long id' para hacer match con el Modelo y el Repository
    public Aeronave getAeronaveId(Long id) {
        return aeronaveRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La aeronave con ID " + id + " no existe en la DGAC."));
    }

    public Aeronave updateAeronave(Aeronave aeronave) {
        // En JPA, save() actúa como un UPSERT. Si el ID ya existe en la BD, lo actualiza.
        return aeronaveRepository.save(aeronave);
    }

    // CORREGIDO: Cambiado 'int id' a 'Long id'
    public String deleteAeronave(Long id) {
        // Buena práctica: Verificar si existe antes de borrar para evitar un EmptyResultDataAccessException
        if (!aeronaveRepository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: La aeronave con ID " + id + " no existe.");
        }
        aeronaveRepository.deleteById(id);
        return "Aeronave eliminada exitosamente del registro DGAC.";
    }

    // LA ACCIÓN LA HACE EL SERVICE (Usa el método count nativo de JpaRepository)
    public int totalAeronaves() {
        return (int) aeronaveRepository.count();
    }

    // LA ACCIÓN LA HACE EL REPOSITORIO (Llama a tu método personalizado @Query o default)
    public int totalAeronavesV2() {
        return aeronaveRepository.totalAeronaves();
    }

    public List<Aeronave> obtenerPorPatente(String patente) {
        return aeronaveRepository.selectPorPatente(patente);
    }

    public List<Aeronave> obtenerPorNumeroSerie(String numeroSerie) {
        return aeronaveRepository.selectPorNumeroSerie(numeroSerie);
    }

    public List<Aeronave> obtenerPorEmpresaProveedora(Long idEmpresaProveedora) {
        return aeronaveRepository.selectPorEmpresaProveedora(idEmpresaProveedora);
    }
}
