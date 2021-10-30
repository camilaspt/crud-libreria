package cami.Library.contollers;

import cami.Library.excepciones.AutorExcepciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cami.Library.servicios.autorServicio;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/autor")
public class autorControlador {

    @Autowired
    private autorServicio AutorServicio;

    @GetMapping("/registro")
    public String registroAutor() {
        return "registerAutor";
    }

    @PostMapping("/save")
    public String save(@RequestParam String nombre, Model model) {

        try {
            AutorServicio.registrarAutor(nombre);
        } catch (AutorExcepciones ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "registerAutor";
    }

    @GetMapping("/lista")
    public String listAllautor(Model model) {
        model.addAttribute("autores", AutorServicio.listAll());
        return "listAutor";
    }

    @GetMapping("/eliminar")
    public String eliminarAutor(Model model) {
        model.addAttribute("autores", AutorServicio.listAll());
        return "deleteAutor";
    }

    @PostMapping("/delete")
    public String deleteAutor(@RequestParam String id) {
        AutorServicio.deleteAutor(id);
        return "redirect:/autor/lista";
    }

    @GetMapping("/modificar")
    public String modificarAutor(Model model) {
        model.addAttribute("autores", AutorServicio.listAll());
        return "modifyAutor";
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam String id, @RequestParam(required = true) String nuevonombre) {
        AutorServicio.modificarAutor(id, nuevonombre);
        return "redirect:/autor/lista";
    }
}
