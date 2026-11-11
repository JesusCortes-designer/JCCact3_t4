package com.example.demoCrud.service;

import com.example.demoCrud.dto.LibroRequest;
import com.example.demoCrud.dto.LibroResponse;
import com.example.demoCrud.exception.ApiException;
import com.example.demoCrud.models.Autor;
import com.example.demoCrud.models.Libro;
import com.example.demoCrud.repository.AutorRepository;
import com.example.demoCrud.repository.LibroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    public LibroService(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    @Transactional(readOnly = true)
    public List<LibroResponse> listar() {
        return libroRepository.findAll().stream().map(LibroResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public LibroResponse obtener(Long id) {
        return LibroResponse.from(buscar(id));
    }

    @Transactional
    public LibroResponse crear(LibroRequest req) {
        validar(req);
        Libro libro = new Libro();
        aplicar(libro, req);
        return LibroResponse.from(libroRepository.save(libro));
    }

    @Transactional
    public LibroResponse actualizar(Long id, LibroRequest req) {
        validar(req);
        Libro libro = buscar(id);
        aplicar(libro, req);
        return LibroResponse.from(libroRepository.save(libro));
    }

    @Transactional
    public void eliminar(Long id) {
        libroRepository.delete(buscar(id));
    }

    private void aplicar(Libro libro, LibroRequest req) {
        Autor autor = autorRepository.findById(req.getAutorId())
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST,
                        "El autor con id " + req.getAutorId() + " no existe"));
        libro.setTitulo(req.getTitulo().trim());
        libro.setAnioPublicacion(req.getAnioPublicacion());
        libro.setAutor(autor);
    }

    private Libro buscar(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Libro no encontrado: " + id));
    }

    private void validar(LibroRequest req) {
        if (req.getTitulo() == null || req.getTitulo().isBlank()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "El título es obligatorio");
        }
        if (req.getAutorId() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Debes indicar el autor (autorId)");
        }
    }
}
