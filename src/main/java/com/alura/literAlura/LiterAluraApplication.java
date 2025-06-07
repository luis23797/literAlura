package com.alura.literAlura;

import com.alura.literAlura.repository.AutoresRepository;
import com.alura.literAlura.main.Main;
import com.alura.literAlura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private AutoresRepository autoresRepository;
	@Autowired
	private LibrosRepository librosRepository;
	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(autoresRepository, librosRepository);
		main.mostrarMenu();
	}
}
