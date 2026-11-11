package com.example.demoCrud.controllers;

import com.example.demoCrud.dto.AutorRequest;
import com.example.demoCrud.dto.AutorResponse;
import com.example.demoCrud.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorRestController {

    private final AutorService autorService;

    public AutorRestController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public List<AutorResponse> listar() {
        return autorService.listar();
    }

    @GetMapping("/{id}")
    public AutorResponse obtener(@PathVariable Long id) {
        return autorService.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutorResponse crear(@RequestBody AutorRequest req) {
        return autorService.crear(req);
    }

    @PutMapping("/{id}")
    public AutorResponse actualizar(@PathVariable Long id, @RequestBody AutorRequest req) {
        return autorService.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        autorService.eliminar(id);
    }
}
