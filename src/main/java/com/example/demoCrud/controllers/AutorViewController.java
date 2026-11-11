package com.example.demoCrud.controllers;

import com.example.demoCrud.dto.AutorRequest;
import com.example.demoCrud.dto.AutorResponse;
import com.example.demoCrud.exception.ApiException;
import com.example.demoCrud.service.AutorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/autores")
public class AutorViewController {

    private final AutorService autorService;

    public AutorViewController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("autores", autorService.listar());
        return "autores/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("autor", new AutorRequest());
        model.addAttribute("accion", "/autores");
        model.addAttribute("titulo", "Nuevo autor");
        return "autores/form";
    }

    @PostMapping
    public String crear(@ModelAttribute("autor") AutorRequest req, Model model, RedirectAttributes ra) {
        try {
            autorService.crear(req);
            ra.addFlashAttribute("ok", "Autor creado correctamente");
            return "redirect:/autores";
        } catch (ApiException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("accion", "/autores");
            model.addAttribute("titulo", "Nuevo autor");
            return "autores/form";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        AutorResponse actual = autorService.obtener(id);
        model.addAttribute("autor", new AutorRequest(actual.nombre(), actual.nacionalidad()));
        model.addAttribute("accion", "/autores/" + id);
        model.addAttribute("titulo", "Editar autor");
        return "autores/form";
    }

    @PostMapping("/{id}")
    public String actualizar(@PathVariable Long id, @ModelAttribute("autor") AutorRequest req,
                             Model model, RedirectAttributes ra) {
        try {
            autorService.actualizar(id, req);
            ra.addFlashAttribute("ok", "Autor actualizado correctamente");
            return "redirect:/autores";
        } catch (ApiException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("accion", "/autores/" + id);
            model.addAttribute("titulo", "Editar autor");
            return "autores/form";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        try {
            autorService.eliminar(id);
            ra.addFlashAttribute("ok", "Autor eliminado correctamente");
        } catch (ApiException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/autores";
    }
}
