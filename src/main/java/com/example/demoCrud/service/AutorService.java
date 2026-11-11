package com.example.demoCrud.service;

import com.example.demoCrud.dto.AutorRequest;
import com.example.demoCrud.dto.AutorResponse;
import com.example.demoCrud.exception.ApiException;
import com.example.demoCrud.models.Autor;
import com.example.demoCrud.repository.AutorRepository;
import com.example.demoCrud.repository.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public AutorService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional(readOnly = true)
    public List<AutorResponse> listar() {
        return autorRepository.findAll().stream().map(AutorResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AutorResponse obtener(Long id) {
        return AutorResponse.from(buscar(id));
    }

    @Transactional
    public AutorResponse crear(AutorRequest req) {
        validar(req);
        Autor autor = new Autor();
        autor.setNombre(req.getNombre().trim());
        autor.setNacionalidad(req.getNacionalidad());
        return AutorResponse.from(autorRepository.save(autor));
    }

    @Transactional
    public AutorResponse actualizar(Long id, AutorRequest req) {
        validar(req);
        Autor autor = buscar(id);
        autor.setNombre(req.getNombre().trim());
        autor.setNacionalidad(req.getNacionalidad());
        return AutorResponse.from(autorRepository.save(autor));
    }

    @Transactional
    public void eliminar(Long id) {
        Autor autor = buscar(id);
        // Regla de negocio: no se puede borrar un autor que aún tiene libros
        if (libroRepository.existsByAutorId(id)) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "No se puede eliminar el autor porque tiene libros registrados");
        }
        autorRepository.delete(autor);
    }

    private Autor buscar(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Autor no encontrado: " + id));
    }

    private void validar(AutorRequest req) {
        if (req.getNombre() == null || req.getNombre().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El nombre del autor es obligatorio");
        }
    }
}
