package com.alura.literAlura.main;

import com.alura.literAlura.model.Autor;
import com.alura.literAlura.model.DatosAutor;
import com.alura.literAlura.model.Libro;
import com.alura.literAlura.repository.AutoresRepository;
import com.alura.literAlura.repository.LibrosRepository;
import com.alura.literAlura.service.ConsumoAPI;
import com.alura.literAlura.service.ConvierteDatos;
import com.alura.literAlura.model.Resultados;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private String apiURL = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private AutoresRepository autoresRepository;
    private LibrosRepository librosRepository;
    public Main(AutoresRepository autoresRepository, LibrosRepository librosRepository) {
        this.autoresRepository = autoresRepository;
        this.librosRepository = librosRepository;
    }

    public void mostrarMenu(){
        System.out.println("""
                1 - Buscar libro
                2 - 
                
                
                """);

        buscarLibro();

    }
    private void buscarLibro(){
        System.out.println("Introduce el nombre del libro");
        String busqueda = teclado.nextLine();
        Resultados resultado = consulta(busqueda);
        DatosAutor datosAutor = resultado.libros().get(0).autores().get(0);
        Optional<Autor> autor = autoresRepository.obtenerAutor(datosAutor.nombre());
        if(autor.isPresent()){
            Optional<Libro> libro = librosRepository.obtenerLibro(autor.get());
            if(libro.isPresent()){
                System.out.println("El libro ya estaba registrado");
                System.out.println(libro.get());
            }else{
                Libro nuevoLibro = crearLibro(resultado);
                nuevoLibro.setAutor(autor.get());
                librosRepository.save(nuevoLibro);
            }
        }else{
            Autor nuevoAutor = crearAutor(resultado);
            autoresRepository.save(nuevoAutor);
            Libro libro = crearLibro(resultado);
            libro.setAutor(nuevoAutor);
            librosRepository.save(libro);
        }
    }

    private Resultados consulta(String param){
        String json = consumoAPI.obtenerDatos(apiURL+param.replace(" ","+"));
        Resultados resultados = conversor.convierteDatos(json,Resultados.class);
        return resultados;
    }
    private Autor crearAutor(Resultados resultado){
        return  new Autor(resultado.libros().get(0).autores().get(0));
    }
    private Libro crearLibro(Resultados resultado){
        return  new Libro(resultado.libros().get(0));
    }
}
