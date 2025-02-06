package com.social.book_network.handler;

import com.social.book_network.exception.OperationNotPermittedException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

@RestControllerAdvice //RestControllerAdvice is registered as a spring bean and ExceptionHandler can be applied globally,
                      //So any exception happened in controller, that exception go through this. This is the combination of @ControllerAdvice
                      //and @RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handler(LockedException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BuisinessErrorCodes.ACCOUNT_LOCKED.getCode())
                        .businessErrorDescription(BuisinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handler(DisabledException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BuisinessErrorCodes.ACCOUNT_DISABLED.getCode())
                        .businessErrorDescription(BuisinessErrorCodes.ACCOUNT_DISABLED.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handler(BadCredentialsException exp){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BuisinessErrorCodes.BAD_CREDENTIALS.getCode())
                        .businessErrorDescription(BuisinessErrorCodes.BAD_CREDENTIALS.getDescription())
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MessagingException.class) // This will come if there is any send email issue.
    public ResponseEntity<ExceptionResponse> handler(MessagingException exp){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(OperationNotPermittedException.class) // This will come if there is any send email issue.
    public ResponseEntity<ExceptionResponse> handler(OperationNotPermittedException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .error(exp.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // This will come if there is any issue raised by @Valid annotation.
    public ResponseEntity<ExceptionResponse> handler(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .validationErrors(errors)
                        .build()
                );
    }

    @ExceptionHandler(Exception.class) // This will come if there is any exception which is not handled by our application.
    public ResponseEntity<ExceptionResponse> handler(Exception exp){
        //log the exception for better understanding
        exp.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .businessErrorDescription("Internal error, Please contact admin")
                        .error(exp.getMessage())
                        .build()
                );
    }
}
