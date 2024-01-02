package com.hostfully.propertymanagement.exceptions;


import jakarta.annotation.Priority;
import com.hostfully.propertymanagement.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Priority(1)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        Response response = new Response("An Error Occurred During Processing The Request.");
        logger.error("An Error Occurred During Processing The Request.");
        ex.printStackTrace();
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<Object> handleDataNotFoundExceptions(DataNotFoundException ex, WebRequest request) {
        Response response = new Response(ex.getMessage());
        logger.error(ex.getMessage());
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataConflictException.class)
    public final ResponseEntity<Object> handleDataNotFoundExceptions(DataConflictException ex, WebRequest request) {
        Response response = new Response(ex.getMessage());
        logger.error(ex.getMessage());
        return new ResponseEntity(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidInputException.class)
    public final ResponseEntity<Object> handleDataFormatExceptions(InvalidInputException ex, WebRequest request) {
        Response response = new Response(ex.getMessage());
        logger.error(ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerException.class)
    public final ResponseEntity<Object> handleInternalServerExceptions(Exception ex, WebRequest request) {
        Response response = new Response("InternalServerException Occurred During Processing The Request.");
        logger.error("InternalServerException Occurred During Processing The Request.");
        ex.printStackTrace();
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}