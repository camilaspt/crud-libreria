package cami.Library.servicios;

import cami.Library.entidades.Autor;
import cami.Library.excepciones.AutorExcepciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cami.Library.repositorios.autorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class autorServicio {

    @Autowired
    private autorRepositorio autorRepositorio;

    @Transactional
    public void registrarAutor(String nombre) throws AutorExcepciones {

        if (nombre.isEmpty() || nombre == null) {
            throw new AutorExcepciones("El nombre del autor no puede ser nulo");
        }
        if (autorRepositorio.findByNombreIgnoreCase(nombre) != null) {
            throw new AutorExcepciones("El autor ya se encuentra registrado");
        }
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autorRepositorio.save(autor);

    }

    public List<Autor> listAll() {
        return autorRepositorio.listOrderByNombre();
    }

    public Autor findById(Autor autor) {
        Optional<Autor> optional = autorRepositorio.findById(autor.getId());
        if (optional.isPresent()) {
            autor = optional.get();
        }
        return autor;
    }

    public void deleteAutor(String id) {

        Optional<Autor> autor = autorRepositorio.findById(id);
        if (autor.isPresent()) {
            autorRepositorio.delete(autor.get());
        }
    }

    public void modificarAutor(String id, String nuevonombre) {

        Optional<Autor> autor = autorRepositorio.findById(id);
        if (autor.isPresent()) {
            autor.get().setNombre(nuevonombre);
            autorRepositorio.save(autor.get());
        }

    }

}
