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

        @GetMapping("/{id}") // BUENA PRÁCTICA: Se agrega la barra suelta para evitar problemas de rutas
        public ResponseEntity<Aeronave> buscarAeronave(@PathVariable Long id) { 
                // NOTA: Ya no es necesario el bloque "if (aeronave == null)" ni lanzar ResourceNotFoundException a mano.
                // El AeronaveService ahora lanza EntityNotFoundException automáticamente,
                // y el GlobalExceptionHandler lo traduce a un error 404 limpio.
                Aeronave aeronave = aeronaveService.getAeronaveId(id);
                return ResponseEntity.ok(aeronave);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Aeronave> actualizarAeronave(
                        @PathVariable Long id, // CORREGIDO: Cambiado de int a Long para sincronizar con el Mapper
                        @Valid @RequestBody UpdateAeronaveRequest request) {
                
                // Ahora calza perfecto con AeronaveMapper.toModel(Long, request) y el color rojo se borra
                Aeronave aeronaveActualizada = aeronaveService.updateAeronave(AeronaveMapper.toModel(id, request));
                return ResponseEntity.ok(aeronaveActualizada);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminarAeronave(@PathVariable Long id) { 
                aeronaveService.deleteAeronave(id);
                return ResponseEntity.noContent().build(); // 204 No Content
        }

        @GetMapping("/total")
        public ResponseEntity<Integer> totalAeronaves() {
                int total = aeronaveService.totalAeronavesV2();
                return ResponseEntity.ok(total);
        }

        @GetMapping("/patente/{patente}")
        public ResponseEntity<List<Aeronave>> selectPorPatente(@PathVariable String patente) { // BUENA PRÁCTICA: Envuelto en ResponseEntity
                return ResponseEntity.ok(aeronaveService.obtenerPorPatente(patente));
        }

        @GetMapping("/numeroSerie/{numeroSerie}")
        public ResponseEntity<List<Aeronave>> selectPorNumeroSerie(@PathVariable String numeroSerie) { // BUENA PRÁCTICA: Envuelto en ResponseEntity
                return ResponseEntity.ok(aeronaveService.obtenerPorNumeroSerie(numeroSerie));
        }

        @GetMapping("/empresa/{idEmpresaProveedora}")
        public ResponseEntity<List<Aeronave>> buscarPorEmpresa(@PathVariable Long idEmpresaProveedora) {
        List<Aeronave> aeronaves = aeronaveService.obtenerPorEmpresaProveedora(idEmpresaProveedora);
        return ResponseEntity.ok(aeronaves);
    }
}