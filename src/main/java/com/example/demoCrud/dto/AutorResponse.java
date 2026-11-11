package com.example.demoCrud.dto;

import com.example.demoCrud.models.Autor;

public record AutorResponse(Long id, String nombre, String nacionalidad, int totalLibros) {

    public static AutorResponse from(Autor a) {
        return new AutorResponse(a.getId(), a.getNombre(), a.getNacionalidad(), a.getLibros().size());
    }
}
