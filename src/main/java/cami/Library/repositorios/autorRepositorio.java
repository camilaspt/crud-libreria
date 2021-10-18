package cami.Library.repositorios;

import cami.Library.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface autorRepositorio extends JpaRepository<Autor, String> {

    @Query("select a from Autor a order by a.nombre")
    List<Autor> listOrderByNombre();

    Autor findByNombreIgnoreCase(String nombre);
}
