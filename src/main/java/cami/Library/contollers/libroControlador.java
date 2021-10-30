package cami.Library.contollers;

import cami.Library.entidades.Autor;
import cami.Library.entidades.Libro;
import cami.Library.excepciones.LibroExcepciones;
import cami.Library.servicios.autorServicio;
import cami.Library.servicios.editorialServicio;
import cami.Library.servicios.libroServicio;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String saveLibro(Model model, RedirectAttributes redirectAttributes, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresprest, @RequestParam String idautor, @RequestParam String ideditorial) {
        try {
            libroServicio.registrarLibro(isbn, titulo, anio, ejemplares, ejemplaresprest, ejemplaresprest, idautor, ideditorial);
        } catch (LibroExcepciones ex) {
            model.addAttribute("error", ex.getMessage());
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/libro/lista";
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

    @GetMapping("/busqueda")
    public String busquedaAutor(Model model) {
        model.addAttribute("listado", autorServicio.listAll());
        return "busqueda";
    }

    @PostMapping("/busqueda")
    public String buscarAutor(@RequestParam String id, Model model) throws Exception {
        model.addAttribute("libros", libroServicio.listaPorAutor(id));
        return "listLibro";
    }

    @GetMapping("/busquedaEditorial")
    public String busquedaEditorial(Model model) {
        model.addAttribute("listado", editorialServicio.listAll());
        return "busquedaEditorial";
    }

    @PostMapping("/busquedaEditorial")
    public String buscarEditorial(@RequestParam String id, Model model) throws Exception {
        model.addAttribute("libros", libroServicio.listaPorEditorial(id));
        return "listLibro";
    }

    @GetMapping("/busquedaTitulo")
    public String busquedaTitulo() {
        return "busquedaNombre";
    }

    @PostMapping("/busquedaTitulo")
    public String buscarTitulo(@RequestParam String titulo, Model model) {
        model.addAttribute("libros", libroServicio.busquedaTitulo(titulo));
        return "listLibro";
    }
}
