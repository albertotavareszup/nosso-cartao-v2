package br.com.zup.nossocartao.compartilhado;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import feign.FeignException;

@RestControllerAdvice
public class ValidationErrorHandler {

	@Autowired
	private MessageSource messageSource;

	private static final Logger log = LoggerFactory
			.getLogger(ValidationErrorHandler.class);

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ValidationErrorsOutputDto handleValidationError(
			MethodArgumentNotValidException exception) {

		List<ObjectError> globalErrors = exception.getBindingResult()
				.getGlobalErrors();
		List<FieldError> fieldErrors = exception.getBindingResult()
				.getFieldErrors();

		return buildValidationErrors(globalErrors, fieldErrors);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ValidationErrorsOutputDto handleValidationError(
			BindException exception) {

		List<ObjectError> globalErrors = exception.getBindingResult()
				.getGlobalErrors();
		List<FieldError> fieldErrors = exception.getBindingResult()
				.getFieldErrors();

		return buildValidationErrors(globalErrors, fieldErrors);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ValidationErrorsOutputDto handleValidationError(
			HttpMessageNotReadableException exception) {
		log.error("Problema na de desserializar o objeto", exception);

		InvalidFormatException invalidFormat = (InvalidFormatException) exception
				.getCause();

		List<ObjectError> globalErrors = List.of(new ObjectError("",
				invalidFormat.getValue() + " não é um valor válido"));
		List<FieldError> fieldErrors = List.of();

		return buildValidationErrors(globalErrors, fieldErrors);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ValidationErrorsOutputDto> handleCustomStatusException(
			ResponseStatusException exception) {
		List<ObjectError> globalErrors = List.of(new ObjectError("",
				exception.getReason() != null ? exception.getReason()
						: "Os dados enviados não puderam ser processados"));

		List<FieldError> fieldErrors = List.of();

		ValidationErrorsOutputDto output = buildValidationErrors(globalErrors,
				fieldErrors);

		return ResponseEntity.status(exception.getStatus()).body(output);

	}

	private ValidationErrorsOutputDto buildValidationErrors(
			List<ObjectError> globalErrors, List<FieldError> fieldErrors) {
		ValidationErrorsOutputDto validationErrors = new ValidationErrorsOutputDto();

		globalErrors.forEach(
				error -> validationErrors.addError(getErrorMessage(error)));

		fieldErrors.forEach(error -> {
			String errorMessage = getErrorMessage(error);
			validationErrors.addFieldError(error.getField(), errorMessage);
		});
		return validationErrors;
	}

	private String getErrorMessage(ObjectError error) {
		return messageSource.getMessage(error, LocaleContextHolder.getLocale());
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<ValidationErrorsOutputDto> handleFeignException(
			FeignException exception) {
		return handleCustomStatusException(new ResponseStatusException(
				HttpStatus.valueOf(exception.status())));
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ValidationErrorsOutputDto> handleEntityNotFoundException(
			EntityNotFoundException exception) {
		return handleCustomStatusException(new ResponseStatusException(
				HttpStatus.NOT_FOUND));
	}

}
