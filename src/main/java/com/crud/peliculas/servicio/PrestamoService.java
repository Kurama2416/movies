package com.crud.peliculas.servicio;

import com.crud.peliculas.modelo.Prestamo;
import com.crud.peliculas.repositorio.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private SocioService socioService;

    @Autowired
    private PeliculaService peliculaService;

    public List<Prestamo> listarPrestamos() {
        return prestamoRepositorio.findAllWithSocioAndPelicula();
    }

    public List<Prestamo> listarPrestamosActivos() {
        return prestamoRepositorio.findByFechaDevolucionIsNullWithSocioAndPelicula();
    }

    public Optional<Prestamo> obtenerPrestamoPorId(Long id) {
        return prestamoRepositorio.findByIdWithSocioAndPelicula(id);
    }

    public Prestamo guardarPrestamo(Prestamo prestamo) {
        // Validar que el socio existe
        socioService.obtenerSocioPorId(prestamo.getSocio().getId())
                .orElseThrow(() -> new IllegalArgumentException("Socio no encontrado"));

        // Validar que la película existe
        peliculaService.obtenerPeliculaPorId(prestamo.getPelicula().getId())
                .orElseThrow(() -> new IllegalArgumentException("Película no encontrada"));

        // Validar que la película esté disponible
        if (!peliculaService.estaDisponibleParaPrestamo(prestamo.getPelicula().getId())) {
            throw new IllegalStateException("La película no está disponible para préstamo");
        }

        // Establecer fecha de préstamo si no viene
        if (prestamo.getFechaPrestamo() == null) {
            prestamo.setFechaPrestamo(LocalDate.now());
        }

        return prestamoRepositorio.save(prestamo);
    }

    public void marcarComoDevuelto(Long prestamoId) {
        Optional<Prestamo> prestamoOpt = prestamoRepositorio.findByIdWithSocioAndPelicula(prestamoId);
        if (prestamoOpt.isPresent()) {
            Prestamo prestamo = prestamoOpt.get();
            prestamo.setFechaDevolucion(LocalDate.now());
            prestamoRepositorio.save(prestamo);
        }
    }

    public void eliminarPrestamo(Long id) {
        prestamoRepositorio.deleteById(id);
    }

    public long contarPrestamosActivos() {
        return prestamoRepositorio.countByFechaDevolucionIsNull();
    }
}