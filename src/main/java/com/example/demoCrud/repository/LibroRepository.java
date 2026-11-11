package com.example.demoCrud.repository;

import com.example.demoCrud.models.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Spring Data genera la consulta SQL a partir del nombre del método
    boolean existsByAutorId(Long autorId);
}
