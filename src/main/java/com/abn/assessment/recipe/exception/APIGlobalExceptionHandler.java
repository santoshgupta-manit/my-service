package com.abn.assessment.recipe.exception;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.abn.assessment.recipe.enums.StatusEnum;
import com.abn.assessment.recipe.model.response.ServiceMessage;
import com.abn.assessment.recipe.model.response.ServiceResponse;
import com.abn.assessment.recipe.service.MessageProvider;



@ControllerAdvice
public class APIGlobalExceptionHandler {

	private final MessageProvider messageProvider;

	public APIGlobalExceptionHandler(MessageProvider messageProvider) {
		this.messageProvider = messageProvider;
	}

	@ExceptionHandler({ ClassNotFoundException.class, ResourceNotFound.class })
	public ResponseEntity<ExceptionMessage> handleNotFoundException(Exception exception, WebRequest request) {

		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(getServiceMessage(StatusEnum.STATUS_404,
				exception.getMessage() == null ? StatusEnum.STATUS_404.getDescription() : exception.getMessage()));
		return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ServiceResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {

		exception.getName();
		exception.getRequiredType();
		String message = exception.getName() + " must be a valid value of type " + exception.getRequiredType();
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(getServiceMessage(StatusEnum.STATUS_400, message));
		return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ServiceResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException exception) {

		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		Set<FieldError> fieldErrorsSet = Set.copyOf(fieldErrors);
		String errorMessage = fieldErrorsSet.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.joining(", "));

		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(getServiceMessage(StatusEnum.STATUS_400, errorMessage));
		return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ IllegalArgumentException.class, InvalidDataAccessApiUsageException.class })
	public ResponseEntity<ExceptionMessage> handleBadRequestException(Exception exception, WebRequest request) {

		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(getServiceMessage(StatusEnum.STATUS_400, exception.getMessage()));
		return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionMessage> handleHttpMessageNotReadableException(
			HttpMessageNotReadableException exception) {
		String message = messageProvider.getMessage("json.invalidFormat");
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(getServiceMessage(StatusEnum.STATUS_400, message));
		return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ExceptionMessage> handleHttpMethodTypeException(Exception exception) {

		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(getServiceMessage(StatusEnum.STATUS_405, exception.getMessage()));
		return new ResponseEntity<>(exceptionMessage, HttpStatus.METHOD_NOT_ALLOWED);

	}

	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class, HttpMediaTypeNotSupportedException.class })
	public ResponseEntity<String> handleHttpMediaTypeException(Exception exception, WebRequest request) {
		HttpStatus status = null;
		String message = "";
		if (exception instanceof HttpMediaTypeNotSupportedException) {
			status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
			message = StatusEnum.STATUS_415.getDescription();
		} else if (exception instanceof HttpMediaTypeNotAcceptableException) {
			status = HttpStatus.NOT_ACCEPTABLE;
			message = StatusEnum.STATUS_415.getDescription();
		}
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(getServiceMessage(StatusEnum.STATUS_406, exception.getMessage()));
		return new ResponseEntity<>(message, status);

	}

	@ExceptionHandler({RuntimeException.class })
	public ResponseEntity<ExceptionMessage> handleGlobalException(Exception exception, WebRequest request) {
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		exceptionMessage.setMessage(getServiceMessage(StatusEnum.STATUS_500, exception.getMessage()));
		return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	private ServiceMessage getServiceMessage(StatusEnum statusEnum, String description) {
		String type = statusEnum.getHttpStatus().getReasonPhrase();
		HttpStatus code = statusEnum.getHttpStatus();
		return new ServiceMessage(type, code.value(), description);
	}

}
