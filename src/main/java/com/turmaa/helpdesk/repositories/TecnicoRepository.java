package com.turmaa.helpdesk.repositories;

import java.util.Optional; // Import necessário para a classe Optional
import org.springframework.data.jpa.repository.JpaRepository;
import com.turmaa.helpdesk.domain.Tecnico;

/**
 * Interface Repository para a entidade Tecnico.
 * Ao estender JpaRepository, a interface herda automaticamente métodos de CRUD
 * (Create, Read, Update, Delete) para a entidade Tecnico, como save(), findById(), findAll(), etc.
 */
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
	
	/**
	 * Declaração de um "Query Method" do Spring Data JPA.
	 *
	 * O Spring Data JPA cria automaticamente a implementação deste método em tempo de execução
	 * com base no seu nome. O padrão "findBy<NomeDoCampo>" gera uma consulta
	 * que busca um registro na tabela pelo campo correspondente (neste caso, 'email').
	 *
	 * @param email O e-mail do técnico a ser buscado no banco de dados.
	 * @return Um Optional<Tecnico> que conterá o técnico se encontrado, ou estará vazio
	 * caso contrário. Usar Optional é uma boa prática para evitar NullPointerExceptions.
	 */
	Optional<Tecnico> findByEmail(String email);

}