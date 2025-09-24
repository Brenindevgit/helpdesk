package com.turmaa.helpdesk.services.exceptions;

import java.io.Serializable;

/**
 * Classe auxiliar (POJO) para encapsular informações sobre um erro de validação de campo.
 * Utilizada pela classe ValidationError para criar uma lista de erros a ser retornada
 * no corpo da resposta HTTP em caso de um erro de validação (status 400).
 */
public class FieldMessage implements Serializable{
	
	private static final long serialVersionUID= 1L;
	
	private String fieldName;
	private String message;
	
	public FieldMessage() {
		super();
	}
	
	/**
	 * Construtor com parâmetros.
	 * @param fieldName O nome do campo que falhou na validação.
	 * @param message A mensagem de erro associada ao campo.
	 */
	public FieldMessage(String fieldName, String message) {
		super();
		this.fieldName = fieldName;
		this.message = message;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}