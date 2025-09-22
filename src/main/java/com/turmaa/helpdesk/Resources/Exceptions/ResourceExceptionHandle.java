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

@ControllerAdvice /*
					 * serve para criar um único lugar para capturar e tratar exceções que podem ser
					 * lançadas por qualquer um dos controladores.
					 */
public class ResourceExceptionHandle {

	@ExceptionHandler(ObjectNotFoundException.class) /*
														 * Quando esta anotação é combinada com a
														 * anotação @ControllerAdvice, ela se torna global. Isso
														 * significa que o método de tratamento de exceções pode
														 * capturar e lidar com exceções lançadas por qualquer
														 * controlador na sua aplicação.
														 */
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
			HttpServletRequest request) {
		/*
		 * O método objectNotFoundException atua como um manipulador de exceções
		 * (@ExceptionHandler). Ele é projetado para ser executado sempre que a exceção
		 * ObjectNotFoundException é lançada em sua aplicação. O método recebe dois
		 * parâmetros: ex: A exceção ObjectNotFoundException que foi capturada. request:
		 * O objeto que representa a requisição HTTP original, que é usado para obter a
		 * URI da requisição.
		 */

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				"Object Not Found", ex.getMessage(), request.getRequestURI());

		/*
		 * StandardError error = new StandardError(...): Aqui, um novo objeto
		 * StandardError é criado. Esse objeto classe personalizada que criamos para
		 * padronizar as mensagens de erro da nossa API. Os parâmetros do construtor
		 * são:
		 * 
		 * System.currentTimeMillis(): O timestamp atual, para indicar quando o erro
		 * ocorreu em milissegundos.
		 * 
		 * HttpStatus.NOT_FOUND.value(): O código de status HTTP 404.
		 * 
		 * "Object Not Found": Uma mensagem de erro genérica.
		 * 
		 * ex.getMessage(): A mensagem específica da exceção que foi lançada.
		 * 
		 * request.getRequestURI(): A URI da requisição que gerou o erro.
		 */

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

		/*
		 * Esta linha constrói e retorna a resposta HTTP.
		 * ResponseEntity.status(HttpStatus.NOT_FOUND): Define o status da resposta como
		 * 404 Not Found. .body(error): Coloca o objeto StandardError que acabamos de
		 * criar no corpo da resposta.
		 */

	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
			HttpServletRequest request) {

		StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Data Violation", ex.getMessage(), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);

	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> ValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

//		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
//				"Fields Validation Error", ex.getMessage(), request.getRequestURI());
		
		ValidationError errors = new ValidationError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				"Fields Validation Error", "Erro na validação dos campos", request.getRequestURI());

		for(FieldError x : ex.getBindingResult().getFieldErrors()) {
			errors.addErrors(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);

	}

}
