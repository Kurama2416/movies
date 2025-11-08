package com.crud.peliculas.servicio;

import com.crud.peliculas.modelo.Pelicula;
import com.crud.peliculas.repositorio.PeliculaRepositorio;
import com.crud.peliculas.repositorio.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaService {

    @Autowired
    private PeliculaRepositorio peliculaRepositorio;

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    public List<Pelicula> listarPeliculas() {
        return peliculaRepositorio.findAll();
    }

    public List<Pelicula> listarPeliculasDisponibles() {
        return peliculaRepositorio.findByPrestamosActivosIsEmpty();
    }

    public Pelicula guardarPelicula(Pelicula pelicula) {
        return peliculaRepositorio.save(pelicula);
    }

    public Optional<Pelicula> obtenerPeliculaPorId(Long id) {
        return peliculaRepositorio.findById(id);
    }

    public void eliminarPelicula(Long id) {
        if (prestamoRepositorio.existsByPeliculaId(id)) {
            throw new IllegalStateException("No se puede eliminar una película con préstamos registrados");
        }
        peliculaRepositorio.deleteById(id);
    }

    public boolean estaDisponibleParaPrestamo(Long peliculaId) {
        return !prestamoRepositorio.existsPrestamoActivoByPeliculaId(peliculaId);
    }
}