package com.turmaa.helpdesk.Resources.Exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.turmaa.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.turmaa.helpdesk.services.exceptions.ObjectNotFoundException;

/**
 * Manipulador de exceções global para todos os controllers da aplicação.
 * A anotação @ControllerAdvice permite que esta classe intercepte exceções
 * lançadas por qualquer @RestController, centralizando o tratamento de erros.
 */
@ControllerAdvice
public class ResourceExceptionHandle {

	/**
	 * Manipula exceções do tipo ObjectNotFoundException.
	 * É acionado quando um serviço tenta buscar um objeto que não existe no banco.
	 * @return ResponseEntity com status 404 (NOT_FOUND) e corpo padronizado.
	 */
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
			HttpServletRequest request) {
		
		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Object Not Found", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	/**
	 * Manipula exceções do tipo DataIntegrityViolationException.
	 * Acionado quando uma operação viola uma regra de integridade de dados (ex: CPF duplicado).
	 * @return ResponseEntity com status 400 (BAD_REQUEST) e corpo padronizado.
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Data Violation", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	/**
	 * Manipula exceções do tipo MethodArgumentNotValidException.
	 * Acionado quando a validação de um DTO anotado com @Valid falha.
	 * @return ResponseEntity com status 400 (BAD_REQUEST) e um corpo ValidationError
	 * contendo a lista de todos os campos que falharam na validação.
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Validation Error", "Erro na validação dos campos.", request.getRequestURI());

		// Itera sobre todos os erros de campo encontrados na exceção...
		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			// ...e adiciona cada um na nossa lista de erros personalizada.
			errors.addError(x.getField(), x.getDefaultMessage()); // CORREÇÃO: Método renomeado para addError
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}