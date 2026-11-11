package com.example.demoCrud.controllers;

import com.example.demoCrud.dto.LibroRequest;
import com.example.demoCrud.dto.LibroResponse;
import com.example.demoCrud.service.LibroService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroRestController {

    private final LibroService libroService;

    public LibroRestController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<LibroResponse> listar() {
        return libroService.listar();
    }

    @GetMapping("/{id}")
    public LibroResponse obtener(@PathVariable Long id) {
        return libroService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibroResponse crear(@RequestBody LibroRequest req) {
        return libroService.crear(req);
    }

    @PutMapping("/{id}")
    public LibroResponse actualizar(@PathVariable Long id, @RequestBody LibroRequest req) {
        return libroService.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }
}
