package cl.GestionDrones.v1.aeronaves.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.GestionDrones.v1.aeronaves.dto.CreateAeronaveRequest;
import cl.GestionDrones.v1.aeronaves.dto.UpdateAeronaveRequest;
import cl.GestionDrones.v1.aeronaves.exception.ResourceNotFoundException;
import cl.GestionDrones.v1.aeronaves.mapper.AeronaveMapper;
import cl.GestionDrones.v1.aeronaves.model.Aeronave;
import cl.GestionDrones.v1.aeronaves.service.AeronaveService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/aeronaves")
public class AeronaveController {

        private final AeronaveService aeronaveService;

        // Constructor injection
        public AeronaveController(AeronaveService aeronaveService) {
                this.aeronaveService = aeronaveService;
        }

        @GetMapping
        public ResponseEntity<List<Aeronave>> listarAeronaves() {
                List<Aeronave> aeronaves = aeronaveService.getAeronaves();
                return ResponseEntity.ok(aeronaves);
        }

        @PostMapping
        public ResponseEntity<Aeronave> agregarAeronave(@Valid @RequestBody CreateAeronaveRequest request) {
                // @Valid ejecuta validaciones Jakarta automáticamente (patente, fechas del seguro)
                Aeronave nuevaAeronave = aeronaveService.saveAeronave(AeronaveMapper.toModel(request));
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevaAeronave);
        }

        @GetMapping("{id}")
        public ResponseEntity<Aeronave> buscarAeronave(@PathVariable int id) { // <-- Usa int
                Aeronave aeronave = aeronaveService.getAeronaveId(id);

                if (aeronave == null) {
                        throw new ResourceNotFoundException("Aeronave no encontrada para id: " + id);
                }

                return ResponseEntity.ok(aeronave);
        }

        @PutMapping("{id}")
        public ResponseEntity<Aeronave> actualizarAeronave(@PathVariable int id, // <-- CORREGIDO: Cambiado de Long a int
                        @Valid @RequestBody UpdateAeronaveRequest request) {
                
                // Ahora calza perfecto con AeronaveMapper.toModel(int, request)
                Aeronave aeronaveActualizada = aeronaveService.updateAeronave(AeronaveMapper.toModel(id, request));
                return ResponseEntity.ok(aeronaveActualizada);
        }

        @DeleteMapping("{id}")
        public ResponseEntity<Void> eliminarAeronave(@PathVariable int id) { // <-- Usa int
                aeronaveService.deleteAeronave(id);
                return ResponseEntity.noContent().build(); // 204 No Content
        }

        @GetMapping("/total")
        public ResponseEntity<Integer> totalAeronaves() {
                int total = aeronaveService.totalAeronavesV2();
                return ResponseEntity.ok(total);
        }

        @GetMapping("/patente/{patente}")
        public List<Aeronave> selectPorPatente(@PathVariable String patente) {
        return aeronaveService.obtenerPorPatente(patente);
        }

        @GetMapping("/numeroSerie/{numeroSerie}")
        public List<Aeronave> selectPorNumeroSerie(@PathVariable String numeroSerie) {
        return aeronaveService.obtenerPorNumeroSerie(numeroSerie);
        }
}
