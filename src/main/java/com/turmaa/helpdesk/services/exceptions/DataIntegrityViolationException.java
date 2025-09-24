package com.turmaa.helpdesk.services.exceptions;

/**
 * Exceção personalizada para ser lançada quando uma operação viola
 * a integridade dos dados no banco. Por exemplo, ao tentar cadastrar
 * um CPF ou e-mail que já existe.
 */
public class DataIntegrityViolationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
	 * Construtor que recebe a mensagem de erro e a causa original.
	 * @param message A mensagem detalhando o erro.
	 * @param cause A exceção original que causou este erro.
	 */
	public DataIntegrityViolationException (String message, Throwable cause) {
		super(message, cause);
	}
	
    /**
	 * Construtor que recebe apenas a mensagem de erro.
	 * @param message A mensagem detalhando o erro.
	 */
	public DataIntegrityViolationException(String message) {
		super(message);
	}

}