package br.lara.alisson.challenge.consultalivros;

import br.lara.alisson.challenge.consultalivros.principal.Principal; // Importa sua classe Principal
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	// Remove a injeção direta de LivroRepository aqui se Principal for a responsável por usá-lo.
	// @Autowired
	// private LivroRepository repositorio;

	// Injeta a sua classe Principal.
	// Como Principal é um @Component, o Spring a gerenciará e injetará seus próprios @Autowired.
	@Autowired
	private Principal principal; // Agora o Spring vai injetar a instância de Principal aqui

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Agora, você não cria uma nova instância de Principal.
		// Em vez disso, usa a instância 'principal' que o Spring injetou.
		principal.exibirMenu(); // Chama o menu da instância de Principal gerenciada pelo Spring
	}
}
