package cami.Library.repositorios;

import cami.Library.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface libroRepositorio extends JpaRepository<Libro, String> {

    Libro findByTituloIgnoreCase(String nombre);

    Libro findByIsbn(Long isbn);

    @Query("select l from Libro l order by l.titulo")
    List<Libro> listOrderByTitulo();
}
