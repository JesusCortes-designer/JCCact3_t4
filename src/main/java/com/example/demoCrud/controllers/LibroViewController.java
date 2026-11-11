package com.example.demoCrud.controllers;

import com.example.demoCrud.dto.LibroRequest;
import com.example.demoCrud.dto.LibroResponse;
import com.example.demoCrud.exception.ApiException;
import com.example.demoCrud.service.AutorService;
import com.example.demoCrud.service.LibroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/libros")
public class LibroViewController {

    private final LibroService libroService;
    private final AutorService autorService;

    public LibroViewController(LibroService libroService, AutorService autorService) {
        this.libroService = libroService;
        this.autorService = autorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("libros", libroService.listar());
        return "libros/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("libro", new LibroRequest());
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("accion", "/libros");
        model.addAttribute("titulo", "Nuevo libro");
        return "libros/form";
    }

    @PostMapping
    public String crear(@ModelAttribute("libro") LibroRequest req, Model model, RedirectAttributes ra) {
        try {
            libroService.crear(req);
            ra.addFlashAttribute("ok", "Libro creado correctamente");
            return "redirect:/libros";
        } catch (ApiException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("autores", autorService.listar());
            model.addAttribute("accion", "/libros");
            model.addAttribute("titulo", "Nuevo libro");
            return "libros/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        LibroResponse actual = libroService.obtener(id);
        LibroRequest req = new LibroRequest();
        req.setTitulo(actual.titulo());
        req.setAnioPublicacion(actual.anioPublicacion());
        req.setAutorId(actual.autorId());
        model.addAttribute("libro", req);
        model.addAttribute("autores", autorService.listar());
        model.addAttribute("accion", "/libros/" + id);
        model.addAttribute("titulo", "Editar libro");
        return "libros/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("libro") LibroRequest req,
                             Model model, RedirectAttributes ra) {
        try {
            libroService.actualizar(id, req);
            ra.addFlashAttribute("ok", "Libro actualizado correctamente");
            return "redirect:/libros";
        } catch (ApiException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("autores", autorService.listar());
            model.addAttribute("accion", "/libros/" + id);
            model.addAttribute("titulo", "Editar libro");
            return "libros/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            libroService.eliminar(id);
            ra.addFlashAttribute("ok", "Libro eliminado correctamente");
        } catch (ApiException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/libros";
    }
}
