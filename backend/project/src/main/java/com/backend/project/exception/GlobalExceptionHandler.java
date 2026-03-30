package com.backend.project.exception;

import com.backend.project.interfaces.dto.error.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingJwtTokenException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingJwtToken(MissingJwtTokenException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        404,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        404,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserAlreadyExists(UserAlreadyExistsException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        409,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmailAlreadyInUse(EmailAlreadyInUseException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        409,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(AssetNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleAssetNotFound(AssetNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        404,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handleAssetAlreadyExists(AssetAlreadyExistsException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        409,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(BatchAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleBatchAlreadyExists(BatchAlreadyExistsException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        409,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(TransactionAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleTransactionAlreadyExists(TransactionAlreadyExistsException ex){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponseDTO(
                        409,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(BatchNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleBatchNotFound(BatchNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        404,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleRoleNotFound(RoleNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(
                        404,
                        ex.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO(500, "Unexpected error", Instant.now()));
    }



}
