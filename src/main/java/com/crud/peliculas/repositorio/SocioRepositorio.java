package com.crud.peliculas.repositorio;

import com.crud.peliculas.modelo.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SocioRepositorio extends JpaRepository<Socio, Long> {
    Optional<Socio> findByCorreo(String correo);
    boolean existsByCorreo(String correo);

    boolean existsByCorreoAndIdNot(String correo, Long id);

    @Query("SELECT s FROM Socio s WHERE s.id IN (SELECT p.socio.id FROM Prestamo p WHERE p.fechaDevolucion IS NULL)")
    List<Socio> findSociosConPrestamosActivos();
}