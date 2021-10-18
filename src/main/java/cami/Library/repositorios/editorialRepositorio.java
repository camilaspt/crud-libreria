package cami.Library.repositorios;

import cami.Library.entidades.Editorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface editorialRepositorio extends JpaRepository<Editorial, String> {

    @Query("select e from Editorial e order by e.nombre")
    List<Editorial> listOrderByNombre();

    Editorial findByNombreIgnoreCase(String nombre);

}
