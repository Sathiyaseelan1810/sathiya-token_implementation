package com.spring.authImplementation.tokenSetup.handler;

import com.spring.authImplementation.tokenSetup.dtosUI.UserResponseMessage;
import com.spring.authImplementation.tokenSetup.exception.SqlException;
import com.spring.authImplementation.tokenSetup.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class UserJwtHandler {

    //@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> sQLIntegrityConstraintViolationException(RuntimeException runtimeException){
        return new ResponseEntity<>(runtimeException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //original exception::
//    @ExceptionHandler(value = SQLException.class)
//    public ResponseEntity<String> sQLException(SQLException sqlException){
//        return new ResponseEntity<>(sqlException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(value = SqlException.class)
    public ResponseEntity<UserResponseMessage> sqlException(SqlException sqlException) {
            UserResponseMessage userRegistrationResponseMessage = UserResponseMessage
                    .builder()
                    .developerMessage("ATTN: No Users Found in the DB!")
                    .errorMessage(sqlException.getMessage())
                    .build();
            return new ResponseEntity<>(userRegistrationResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<UserResponseMessage> userAuthenticationException(UserException authenticationException) {
        UserResponseMessage userRegistrationResponseMessage = UserResponseMessage
                .builder()
                .developerMessage("ATTN: "+authenticationException.getMessage())
                .errorMessage("uhihiu")
                .build();
        return new ResponseEntity<>(userRegistrationResponseMessage, HttpStatus.OK);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<List<FieldError>> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrorList = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        return new ResponseEntity<>(fieldErrorList, HttpStatus.BAD_REQUEST);
    }

    // Customized Exception based on the Micro Services:


//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(UserException.class)
//    public Map<String, String> handleUserNotFoundException(UserException exception) {
//        Gson gson = new Gson();
//        Map<String, String> exc_map = new HashMap<String, String>();
//        exc_map.put("message", ex.toString());
//        exc_map.put("stacktrace", getStackTrace(ex));
//        Map<String, String> map =  new HashMap<>();
//        map.put("errorMessage", exception.getMessage());
//        return map;
//    }



}