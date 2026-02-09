package projects.koko.tinder_backend.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projects.koko.tinder_backend.utils.ProfileExistsException;
import projects.koko.tinder_backend.utils.ProfileNotFoundException;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<String> handleProfileNotFoundException(ProfileNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ProfileExistsException.class)
    public ResponseEntity<String> handleProfileExistsException(ProfileExistsException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
