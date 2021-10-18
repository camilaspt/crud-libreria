package cami.Library.contollers;

import cami.Library.entidades.Libro;
import cami.Library.excepciones.LibroExcepciones;
import cami.Library.servicios.autorServicio;
import cami.Library.servicios.editorialServicio;
import cami.Library.servicios.libroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class libroControlador {

    @Autowired
    private libroServicio libroServicio;

    @Autowired
    private autorServicio autorServicio;

    @Autowired
    private editorialServicio editorialServicio;

    @GetMapping("/registro")
    public String registroLibro(Model model) {
        model.addAttribute("autores", autorServicio.listAll());
        model.addAttribute("editoriales", editorialServicio.listAll());
        return "registerLibro";
    }

    @PostMapping("/save")
    public String saveLibro(@RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresprest, @RequestParam String idautor, @RequestParam String ideditorial) throws LibroExcepciones {
        libroServicio.registrarLibro(isbn, titulo, anio, ejemplares, ejemplaresprest, ejemplaresprest, idautor, ideditorial);
        return "redirect:/";
    }

    @GetMapping("/lista")
    public String listaLibros(Model model) {
        model.addAttribute("libros", libroServicio.listAll());
        return "listLibro";
    }

    @GetMapping("/eliminar")
    public String eliminarLibro(Model model) {
        model.addAttribute("libros", libroServicio.listAll());
        return "deleteLibro";
    }

    @PostMapping("/delete")
    public String deleteLibro(@RequestParam String id) {
        libroServicio.delete(id);
        return "redirect:/libro/lista";
    }

    @GetMapping("/modificar")
    public String modificarLibro(Model model) {
        model.addAttribute("libros", libroServicio.listAll());
        model.addAttribute("editoriales", editorialServicio.listAll());
        model.addAttribute("autores", autorServicio.listAll());
        return "modifyLibro";
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam String idLibro, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam String idEditorial, @RequestParam String idAutor) throws LibroExcepciones {
        Libro libro = libroServicio.modificarLibro(idLibro);
        libroServicio.modifica(libro, isbn, titulo, anio, ejemplares, ejemplaresPrestados, idEditorial, idAutor);
        return "redirect:/libro/lista";
    }

}
