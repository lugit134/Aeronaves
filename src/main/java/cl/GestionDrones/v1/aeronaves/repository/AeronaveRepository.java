package cl.GestionDrones.v1.aeronaves.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.GestionDrones.v1.aeronaves.model.Aeronave;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
@Repository
//public class AeronaveRepository {
    // private List<Libro> listaLibros = new ArrayList<Libro>();
public interface AeronaveRepository extends JpaRepository<Aeronave, Integer> {
    // Consulta nativa simple
    @Query(value = "SELECT * FROM Aeronaves WHERE patente = :patente", nativeQuery = true)
    List<Aeronave> selectPorPatente(@Param("patente") String patente);
    //otra alternativa   
    // Libro findByEmailCustom(@Param("editorial") String editorial);

    @Query(value = "SELECT * FROM Aeronaves WHERE numeroSerie = :numeroSerie", nativeQuery = true)
    List<Aeronave> selectPorNumeroSerie(@Param("numeroSerie") String numeroSerie);
 
 
    // Consulta con JOINs nativos
   // @Query(value = "SELECT l.* FROM libros l INNER JOIN editorial e ON l.editorial = e.editorial WHERE e.editorial = ?1", 
    //       nativeQuery = true)
   // List<Libro> findByRoleName(String roleName);


    default int totalAeronaves() {
        return (int) this.count(); // ← "this" se refiere a la instancia del repository
    }
    

}
