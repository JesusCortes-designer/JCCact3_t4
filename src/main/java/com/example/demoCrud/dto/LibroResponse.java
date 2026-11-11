package com.example.demoCrud.dto;

import com.example.demoCrud.models.Libro;

// Incluye el NOMBRE del autor (no solo su ID), como pide la actividad
public record LibroResponse(Long id, String titulo, Integer anioPublicacion, Long autorId, String autorNombre) {

    public static LibroResponse from(Libro l) {
        return new LibroResponse(
                l.getId(),
                l.getTitulo(),
                l.getAnioPublicacion(),
                l.getAutor().getId(),
                l.getAutor().getNombre());
    }
}
