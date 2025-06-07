package com.alura.literAlura.repository;

import com.alura.literAlura.model.Autor;
import com.alura.literAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autor,Long> {
    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE a=:autor ")
    Optional<Libro> obtenerLibro(Autor autor);
    @Query("SELECT a FROM Autor a WHERE a.nombre=:nombre")
    Optional<Autor> obtenerAutor(String nombre);
    @Query("SELECT a FROM Autor a WHERE a.fechaDeFallecimiento>=:fecha AND a.fechaDeNacimiento<=:fecha")
    List<Autor> obtenerAutorVivo(Integer fecha);


}
