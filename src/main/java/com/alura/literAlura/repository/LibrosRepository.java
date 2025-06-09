package com.alura.literAlura.repository;

import com.alura.literAlura.model.Autor;
import com.alura.literAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT l FROM Libro l WHERE l.autor=:autor AND l.titulo=:titulo")
    Optional<Libro> obtenerLibro(Autor autor, String titulo);
    @Query("SELECT l FROM Libro l WHERE l.idioma=:idioma")
    List<Libro> obtenerLibrosPorIdioma(String idioma);

}
