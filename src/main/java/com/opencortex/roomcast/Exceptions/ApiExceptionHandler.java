package com.opencortex.roomcast.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RoomNotFoundException.class})
    protected ResponseEntity<ExceptionResponse> handlerRoomNotFound(RuntimeException exception){

        ResponseEntity<ExceptionResponse> responseEntity = new ResponseEntity<ExceptionResponse>(
                HttpStatus.BAD_REQUEST
        );

        return responseEntity;
    }
}
