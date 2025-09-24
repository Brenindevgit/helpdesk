package com.turmaa.helpdesk.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.turmaa.helpdesk.domain.Pessoa;

/**
 * Interface Repository para a entidade Pessoa.
 * Embora Pessoa seja uma classe abstrata, este repositório permite consultas
 * em campos comuns a todas as suas subclasses (Cliente e Tecnico).
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
	
	/**
	 * Query Method que busca uma Pessoa pelo seu CPF.
	 * O Spring Data JPA gera a implementação automaticamente.
	 * * @param cpf O CPF a ser buscado.
	 * @return Um Optional contendo a Pessoa encontrada, ou vazio se não encontrar.
	 */
	Optional<Pessoa> findByCpf(String cpf);
	
	/**
	 * Query Method que busca uma Pessoa pelo seu e-mail.
	 * Essencial para a validação de e-mails únicos e para o processo de login.
	 * * @param email O e-mail a ser buscado.
	 * @return Um Optional contendo a Pessoa encontrada, ou vazio se não encontrar.
	 */
	Optional<Pessoa> findByEmail(String email);

}