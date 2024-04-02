package com.onlineAssessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
	
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Object> exception(UserNotFoundException exception){
		return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = AnswerNotFoundException.class)
	public ResponseEntity<Object> exception(AnswerNotFoundException exception){
		return new ResponseEntity<>("Option not selected", HttpStatus.NOT_FOUND);                       
	}
	
	@ExceptionHandler(value = QuestionNotFoundException.class)
	public ResponseEntity<Object> exception(QuestionNotFoundException exception){
		return new ResponseEntity<>("Quiz is empty. Please add questions to quiz.", HttpStatus.NOT_FOUND);
	}
}
