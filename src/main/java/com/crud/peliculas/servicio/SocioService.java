package com.crud.peliculas.servicio;

import com.crud.peliculas.modelo.Socio;
import com.crud.peliculas.repositorio.SocioRepositorio;
import com.crud.peliculas.repositorio.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SocioService {

    @Autowired
    private SocioRepositorio socioRepositorio;

    @Autowired
    private PrestamoRepositorio prestamoRepositorio;

    public List<Socio> listarSocios() {
        return socioRepositorio.findAll();
    }

    public Socio guardarSocio(Socio socio) {

        if (socioRepositorio.existsByCorreoAndIdNot(socio.getCorreo(), socio.getId())) {
            throw new IllegalArgumentException("Ya existe un socio con este correo electrónico");
        }
        return socioRepositorio.save(socio);
    }

    public Optional<Socio> obtenerSocioPorId(Long id) {
        return socioRepositorio.findById(id);
    }

    public void eliminarSocio(Long id) {

        if (prestamoRepositorio.existsBySocioIdAndFechaDevolucionIsNull(id)) {
            throw new IllegalStateException("No se puede eliminar un socio con préstamos activos");
        }
        socioRepositorio.deleteById(id);
    }

    public List<Socio> buscarSociosConPrestamosActivos() {
        return socioRepositorio.findSociosConPrestamosActivos();
    }
}