package com.example;

import com.example.model.Pessoa;
import com.example.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.LongStream;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PessoaRepository repository) {
		return args -> {
			repository.deleteAll();
			LongStream.range(1, 11)
					.mapToObj(i -> {
						Pessoa p = new Pessoa();
						p.setNome("Pessoa " + i);
						p.setEmail("pessoa" + i + "@gmail.com");
						p.setSexo("M");
						p.setDataNascimento(Date.valueOf(LocalDate.now()));
						p.setNaturalidade("Florianopolis");
						p.setNacionalidade("Brasileiro");
						p.setCpf("084.173.429-45");
						return p;
					})
					.map(v -> repository.save(v))
					.forEach(System.out::println);
		};
	}
}
