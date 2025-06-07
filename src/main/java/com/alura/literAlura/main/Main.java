package com.alura.literAlura.main;

import com.alura.literAlura.model.*;
import com.alura.literAlura.repository.AutoresRepository;
import com.alura.literAlura.repository.LibrosRepository;
import com.alura.literAlura.service.ConsumoAPI;
import com.alura.literAlura.service.ConvierteDatos;
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
    private String opc="";
    public Main(AutoresRepository autoresRepository, LibrosRepository librosRepository) {
        this.autoresRepository = autoresRepository;
        this.librosRepository = librosRepository;
    }

    public void mostrarMenu(){
        while (!opc.equals("0")){
        System.out.println("""
                1 - Buscar libro
                2 - Listar libros
                3 - Listar Autores
                4-  Listar Autores vivos en un determinado año
                0 - Salir
                """);
        opc = teclado.nextLine();
        switch (opc){
            case "0":
                continue;
            case "1":
                buscarLibro();
                break;
            case "2":
                listarLibros();
                break;
            case "3":
                listarAutores();
                break;
            case "4":
                listarAutoresVivos();
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
        }


    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año vivo de autores que desea buscar");
        Integer fecha;
        try{
            fecha = Integer.valueOf(teclado.nextLine());
        }catch (NumberFormatException e){
            System.out.println("El numero es invalido");
            return;
        }
        List<Autor> autores = autoresRepository.obtenerAutorVivo(fecha);
        if(autores.isEmpty()) {
            System.out.println("No hay registros de autores vivos en esa fecha");
        }else{
            autores.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<Autor> autores = autoresRepository.findAll();
        if(autores.isEmpty()){
            System.out.println("Aun no hay Autores registrados");
            return;
        }
        autores.forEach(System.out::println);
    }

    private void listarLibros() {
        List<Libro> libros = librosRepository.findAll();
        if(libros.isEmpty()){
            System.out.println("Aun no hay libros registrados");
            return;
        }
        libros.forEach(System.out::println);
    }

    private void buscarLibro(){
        System.out.println("Introduce el nombre del libro");
        String busqueda = teclado.nextLine();
        Resultados resultado = consulta(busqueda);
        if(resultado.libros().isEmpty()){
            System.out.println("No se encontro ningun libro");
            return;
        }
        DatosAutor datosAutor = resultado.libros().get(0).autores().get(0);
        DatosLibro datosLibro = resultado.libros().get(0);
        Optional<Autor> autor = autoresRepository.obtenerAutor(datosAutor.nombre());
        if(autor.isPresent()){
            Optional<Libro> libro = librosRepository.obtenerLibro(autor.get(),datosLibro.titulo());
            if(libro.isPresent()){
                System.out.println("El libro ya estaba registrado");
                System.out.println(libro.get());
            }else{
                Libro nuevoLibro = crearLibro(resultado);
                nuevoLibro.setAutor(autor.get());
                librosRepository.save(nuevoLibro);
                System.out.println(nuevoLibro);
            }
        }else{
            Autor nuevoAutor = crearAutor(resultado);
            autoresRepository.save(nuevoAutor);
            Libro libro = crearLibro(resultado);
            libro.setAutor(nuevoAutor);
            librosRepository.save(libro);
            System.out.println(libro);
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
