package com.turmaa.helpdesk.services.exceptions;

/**
 * Exceção personalizada para ser lançada quando uma busca por um objeto
 * no banco de dados não retorna resultados.
 * Herda de RuntimeException para não exigir tratamento de exceção obrigatório (checked exception).
 */
public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Construtor que recebe a mensagem de erro e a causa original.
	 * @param message A mensagem detalhando o erro.
	 * @param cause A exceção original que causou este erro.
	 */
	public ObjectNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Construtor que recebe apenas a mensagem de erro.
	 * @param message A mensagem detalhando o erro.
	 */
	public ObjectNotFoundException(String message) {
		super(message);
	}
	
}