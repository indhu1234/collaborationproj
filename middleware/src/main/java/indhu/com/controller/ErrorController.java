package indhu.com.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import indhu.com.errors.ErrorDetail;

@ControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ErrorDetail> errorList = new LinkedList<ErrorDetail>();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			ErrorDetail error = new ErrorDetail();
			error.setFieldName(fieldError.getField());
			error.setErrorMessage(fieldError.getDefaultMessage());
			errorList.add(error);
		}
		return new ResponseEntity<>(errorList, HttpStatus.BAD_REQUEST);
	}
}
