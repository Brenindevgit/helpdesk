package com.turmaa.helpdesk.Resources.Exceptions;

import java.util.ArrayList;
import java.util.List;
import com.turmaa.helpdesk.services.exceptions.FieldMessage;

/**
 * Classe especializada de erro que estende StandardError.
 * Projetada para carregar uma lista de mensagens de erro de validação de campos,
 * além das informações de erro padrão.
 */
public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;

	// Lista que armazenará os detalhes de cada campo que falhou na validação.
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError() {
		super();
	}

	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	/**
	 * Retorna a lista de erros de campo.
	 * @return Uma lista de objetos FieldMessage.
	 */
	public List<FieldMessage> getErrors() {
		return errors;
	}

	/**
	 * Adiciona um novo erro de campo à lista.
	 * @param fieldName O nome do campo que contém o erro.
	 * @param message A mensagem de erro de validação para o campo.
	 */
	public void addError(String fieldName, String message) {
		this.errors.add(new FieldMessage(fieldName, message));
	}
}