package com.pfms.pfms_analytics.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.pfms.pfms_analytics.enums.ErrorCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Consolidated handler for various validation-related exceptions
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class // Moved here
    })
    public ResponseEntity<ErrorResponseDto> handleValidationExceptions(Exception ex, HttpServletRequest request) {
        List<ValidationException> errors = new ArrayList<>();

        // Handle MethodArgumentNotValidException (used for field-level validation errors)
        if (ex instanceof MethodArgumentNotValidException manvEx) {
            for (FieldError fieldError : manvEx.getBindingResult().getFieldErrors()) {
                errors.add(new ValidationException(fieldError.getField(), fieldError.getDefaultMessage()));
            }
        }

        // Handle MethodArgumentTypeMismatchException (used for type mismatch errors in request parameters)
        else if (ex instanceof MethodArgumentTypeMismatchException matmEx) {
            String field = matmEx.getName();
            String message = String.format("Invalid value '%s' for parameter '%s'", matmEx.getValue(), field);
            errors.add(new ValidationException(field, message));
        }

        // Handle MissingServletRequestParameterException (used when a required request parameter is missing)
        else if (ex instanceof MissingServletRequestParameterException msrpEx) {
            errors.add(new ValidationException(msrpEx.getParameterName(), "Missing required parameter"));
        }

        // Handle HttpMessageNotReadableException (used for malformed JSON or mismatched input structure)
        else if (ex instanceof HttpMessageNotReadableException hmnreEx) {
            Throwable cause = hmnreEx.getCause();
            if (cause instanceof MismatchedInputException mismatchedInput) {
                mismatchedInput.getPath().forEach(ref -> errors.add(new ValidationException(
                        ref.getFieldName() != null ? ref.getFieldName() : "body",
                        "Invalid value type or structure"
                )));
            } else {
                errors.add(new ValidationException("body", "Malformed or unreadable JSON"));
            }
        }

        // Return a bad request with the list of validation errors
        ErrorResponseDto response = ErrorResponseBuilder.build(request, ErrorCodeEnum.VALIDATION_ERROR, null, errors);
        return ResponseEntity.badRequest().body(response);
    }

    // Handler for custom validation exceptions (specific to your application logic)
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomValidation(CustomValidationException ex, HttpServletRequest request) {
        ErrorResponseDto response = ErrorResponseBuilder.build(
                request, ErrorCodeEnum.CUSTOM_VALIDATION, ex.getMessage(), null
        );
        return ResponseEntity.badRequest().body(response);
    }

    // Handle for Already Exists exceptions (409 errors)
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleAlreadyExists(AlreadyExistsException ex, HttpServletRequest request) {
        ErrorResponseDto response = ErrorResponseBuilder.build(request, ErrorCodeEnum.ALREADY_EXISTS,  ex.getMessage(), null);
        return ResponseEntity.status(ErrorCodeEnum.ALREADY_EXISTS.getCode()).body(response);
    }

    // Handler for resource not found (404 errors)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponseDto response = ErrorResponseBuilder.build(request, ErrorCodeEnum.NOT_FOUND,  ex.getMessage(), null);
        return ResponseEntity.status(ErrorCodeEnum.NOT_FOUND.getCode()).body(response);
    }

    // Handler for forbidden access (403 errors)
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseDto> handleForbidden(ForbiddenException ex, HttpServletRequest request) {
        ErrorResponseDto response = ErrorResponseBuilder.build(request, ErrorCodeEnum.FORBIDDEN,  ex.getMessage(), null);
        return ResponseEntity.status(ErrorCodeEnum.FORBIDDEN.getCode()).body(response);
    }

    // Handler for unauthorized access (401 errors)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleUnauthorized(UnauthorizedException ex, HttpServletRequest request) {
        ErrorResponseDto response = ErrorResponseBuilder.build(request, ErrorCodeEnum.UNAUTHORIZED,  ex.getMessage(), null);
        return ResponseEntity.status(ErrorCodeEnum.UNAUTHORIZED.getCode()).body(response);
    }

    // Generic exception handler for any unhandled exceptions (500 internal server errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception ex, HttpServletRequest request) {
        ErrorResponseDto response = ErrorResponseBuilder.build(request, ErrorCodeEnum.INTERNAL_ERROR, ex.getMessage(), null);
        return ResponseEntity.status(ErrorCodeEnum.INTERNAL_ERROR.getCode()).body(response);
    }
}
