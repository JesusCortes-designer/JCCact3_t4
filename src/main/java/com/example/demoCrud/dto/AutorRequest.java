package com.example.demoCrud.dto;

public class AutorRequest {

    private String nombre;
    private String nacionalidad;

    public AutorRequest() {
    }

    public AutorRequest(String nombre, String nacionalidad) {
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
}
