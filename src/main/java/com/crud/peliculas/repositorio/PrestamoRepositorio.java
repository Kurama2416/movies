package com.crud.peliculas.repositorio;

import com.crud.peliculas.modelo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Long> {

    List<Prestamo> findByFechaDevolucionIsNull();

    @Query("SELECT COUNT(p) > 0 FROM Prestamo p WHERE p.pelicula.id = :peliculaId AND p.fechaDevolucion IS NULL")
    boolean existsPrestamoActivoByPeliculaId(Long peliculaId);

    @Query("SELECT p FROM Prestamo p JOIN FETCH p.socio JOIN FETCH p.pelicula")
    List<Prestamo> findAllWithSocioAndPelicula();

    @Query("SELECT p FROM Prestamo p JOIN FETCH p.socio JOIN FETCH p.pelicula WHERE p.fechaDevolucion IS NULL")
    List<Prestamo> findByFechaDevolucionIsNullWithSocioAndPelicula();

    @Query("SELECT p FROM Prestamo p JOIN FETCH p.socio JOIN FETCH p.pelicula WHERE p.id = :id")
    Optional<Prestamo> findByIdWithSocioAndPelicula(Long id);

    boolean existsByPeliculaId(Long peliculaId);
    boolean existsBySocioIdAndFechaDevolucionIsNull(Long socioId);

    List<Prestamo> findBySocioIdOrderByFechaPrestamoDesc(Long socioId);
    List<Prestamo> findByPeliculaIdOrderByFechaPrestamoDesc(Long peliculaId);

    long countByFechaDevolucionIsNull();
}