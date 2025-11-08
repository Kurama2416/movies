package com.crud.peliculas.repositorio;

import com.crud.peliculas.modelo.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PeliculaRepositorio extends JpaRepository<Pelicula, Long> {

    boolean existsByTituloAndDirector(String titulo, String director);


    @Query("SELECT p FROM Pelicula p WHERE p.id NOT IN (SELECT pr.pelicula.id FROM Prestamo pr WHERE pr.fechaDevolucion IS NULL)")
    List<Pelicula> findByPrestamosActivosIsEmpty();


    List<Pelicula> findByGenero(String genero);


    List<Pelicula> findByTituloContainingIgnoreCase(String titulo);


    List<Pelicula> findByDirectorContainingIgnoreCase(String director);


    List<Pelicula> findByAnioEstrenoBetween(int anioInicio, int anioFin);
}