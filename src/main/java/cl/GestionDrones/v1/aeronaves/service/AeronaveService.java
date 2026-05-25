package cl.GestionDrones.v1.aeronaves.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.GestionDrones.v1.aeronaves.model.Aeronave;
import cl.GestionDrones.v1.aeronaves.repository.AeronaveRepository;

@Service
public class AeronaveService {
    @Autowired
    private AeronaveRepository aeronaveRepository;

    public List<Aeronave> getAeronaves() {
        // return aeronaveRepository.obtenerAeronaves();
        return aeronaveRepository.findAll();
    }

    public Aeronave saveAeronave(Aeronave aeronave) {
        // return aeronaveRepository.guardar(aeronave);
        return aeronaveRepository.save(aeronave);
    }

    public Aeronave getAeronaveId(int id) {
        // return aeronaveRepository.buscarPorId(id);
        return aeronaveRepository.findById(id).orElse(null);
    }

    public Aeronave updateAeronave(Aeronave aeronave) {
        // return aeronaveRepository.actualizar(aeronave);
        return aeronaveRepository.save(aeronave);
    }

    public String deleteAeronave(int id) {
        // aeronaveRepository.eliminar(id);
        // return "producto eliminado";
        aeronaveRepository.deleteById(id);
        return "Aeronave eliminada";
    }

    // LA ACCIÓN LA HACE EL SERVICE
    public int totalAeronaves() {
        // return aeronaveRepository.obtenerAeronaves().size();
        return (int) aeronaveRepository.count();
    }

    // LA ACCIÓN LA HACE EL REPOSITORIO
    public int totalAeronavesV2() {
        return aeronaveRepository.totalAeronaves();
    }

    public List<Aeronave> obtenerPorPatente(String patente) {
        return aeronaveRepository.selectPorPatente(patente);
    }
    public List<Aeronave> obtenerPorNumeroSerie(String numeroSerie) {
        return aeronaveRepository.selectPorNumeroSerie(numeroSerie);
    }
}
