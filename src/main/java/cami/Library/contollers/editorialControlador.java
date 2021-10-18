package cami.Library.contollers;

import cami.Library.servicios.editorialServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editorial")
public class editorialControlador {

    @Autowired
    private editorialServicio editorialservicio;

    @GetMapping("/registro")
    public String registroEditorial() {
        return "registerEditorial";
    }

    @PostMapping("/save")
    public String saveEditorial(@RequestParam String nombre) {

        try {
            editorialservicio.registrarEditorial(nombre);
        } catch (Exception e) {
            System.out.println("error cargando editorial");
        }
        return "redirect:/";
    }

    @GetMapping("/lista")
    public String listaEditorial(Model model) {
        model.addAttribute("editoriales", editorialservicio.listAll());
        return "listEditorial";
    }

    @GetMapping("/eliminar")
    public String eliminar(Model model) {
        model.addAttribute("editoriales", editorialservicio.listAll());
        return "deleteEditorial";
    }

    @PostMapping("/delete")
    public String eliminarEditorial(@RequestParam(required = false) String id) {
        editorialservicio.eliminarEditorial(id);
        return "redirect:/editorial/lista";
    }

    @GetMapping("/modificar")
    public String modificarEditorial(Model model) {
        model.addAttribute("editoriales", editorialservicio.listAll());
        return "modifyEditorial";
    }

    @PostMapping("/modificar")
    public String modificarEditorial(@RequestParam String id, @RequestParam(required = true) String nuevonombre) {
        editorialservicio.modificarEditorial(id, nuevonombre);
        return "redirect:/editorial/lista";
    }

}
