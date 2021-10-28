package cami.Library.servicios;

import cami.Library.entidades.Editorial;
import cami.Library.excepciones.EditorialExcepciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cami.Library.repositorios.editorialRepositorio;
import java.util.List;
import java.util.Optional;

@Service
public class editorialServicio {
    
    private editorialRepositorio editorialRepositorio;
    
    public editorialServicio(editorialRepositorio editorialRepositorio1) {
        editorialRepositorio = editorialRepositorio1;
    }
    
    public void registrarEditorial(String nombre) throws EditorialExcepciones {
        
        if (editorialRepositorio.findByNombreIgnoreCase(nombre) == null) {
            Editorial editorial = new Editorial();
            if (nombre.isEmpty()) {
                throw new EditorialExcepciones("El nombre de la editorial no puede estar vacio");
            } else {
                editorial.setNombre(nombre);
            }
            
            editorial.setAlta(true);
            editorialRepositorio.save(editorial);
        }
    }
    
    public List<Editorial> listAll() {
        return editorialRepositorio.listOrderByNombre();
    }
    
    public void eliminarEditorial(String id) {
        Optional<Editorial> editorial = editorialRepositorio.findById(id);
        if (editorial.isPresent()) {
            editorialRepositorio.delete(editorial.get());
        }
    }
    
    public void modificarEditorial(String id, String nuevonombre) {
        
        Optional<Editorial> editorial = editorialRepositorio.findById(id);
        if (editorial.isPresent()) {
            editorial.get().setNombre(nuevonombre);
            editorialRepositorio.save(editorial.get());
        }
        
    }
}
