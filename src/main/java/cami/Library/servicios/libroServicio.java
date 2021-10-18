package cami.Library.servicios;

import cami.Library.entidades.Autor;
import cami.Library.entidades.Editorial;
import cami.Library.entidades.Libro;
import cami.Library.excepciones.LibroExcepciones;
import org.springframework.stereotype.Service;
import cami.Library.repositorios.autorRepositorio;
import cami.Library.repositorios.editorialRepositorio;
import cami.Library.repositorios.libroRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class libroServicio {

    @Autowired
    private libroRepositorio libroRepositorio;

    @Autowired
    private autorRepositorio autorRepositorio;

    @Autowired
    private editorialRepositorio editorialRepositorio;

    public void registrarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Integer ejemplaresRestantes, String idautor, String ideditorial) throws LibroExcepciones {

        if (libroRepositorio.findByTituloIgnoreCase(titulo) == null) {
            Libro libro = new Libro();

            if (libroRepositorio.findByIsbn(isbn) != null) {
                throw new LibroExcepciones("Ya existe un libro registrado bajo ese isbn");
            } else {
                libro.setIsbn(isbn);
            }

            libro.setTitulo(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setEjemplaresRestantes(ejemplaresRestantes);
            libro.setAlta(true);

            Optional<Autor> autor = autorRepositorio.findById(idautor);
            if (autor.isPresent()) {
                libro.setAutor(autor.get());
            }
            Optional<Editorial> editorial = editorialRepositorio.findById(ideditorial);
            if (editorial.isPresent()) {
                libro.setEditorial(editorial.get());
            }

            libroRepositorio.save(libro);
        }
    }

    public List<Libro> listAll() {
        return libroRepositorio.findAll();
    }

    public void delete(String id) {
        Optional<Libro> libro = libroRepositorio.findById(id);
        if (libro.isPresent()) {
            libroRepositorio.delete(libro.get());
        }

    }

    public Libro modificarLibro(String idLibro) throws LibroExcepciones {
        Optional<Libro> libroModificar = libroRepositorio.findById(idLibro);
        if (libroModificar.isPresent()) {
            return libroModificar.get();
        } else {
            throw new LibroExcepciones("No se encontro el libro");
        }
    }

    public void modifica(Libro libro, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String idEditorial, String idAutor) {
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEditorial(editorialRepositorio.findById(idEditorial).get());
        libro.setAutor(autorRepositorio.findById(idAutor).get());
        libroRepositorio.save(libro);
    }
}
