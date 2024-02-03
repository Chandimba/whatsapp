package ao.it.chandsoft.whatsapp.controller.handler;

import ao.it.chandsoft.whatsapp.dto.ExceptionResponse;
import ao.it.chandsoft.whatsapp.exception.MediaFileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AppErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ExceptionResponse httpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return exceptionResponse(HttpStatus.BAD_REQUEST, "Validation error", "Request body is required");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ExceptionResponse exceptionResponse = exceptionResponse(HttpStatus.BAD_REQUEST, "Validation error", "One or more attributes were not validated correctly");
        exceptionResponse.setDetails(toList(ex.getBindingResult().getFieldErrors()));
        return exceptionResponse;
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    ExceptionResponse maxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        ExceptionResponse exceptionResponse = exceptionResponse(HttpStatus.PAYLOAD_TOO_LARGE, "Validation error", ex.getMessage());

        return exceptionResponse;
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MediaFileException.class)
    ExceptionResponse mediaFileException(MediaFileException ex) {
        ExceptionResponse exceptionResponse = exceptionResponse(HttpStatus.PAYLOAD_TOO_LARGE, "Validation error", ex.getMessage());

        return exceptionResponse;
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(IllegalArgumentException.class)
    ExceptionResponse illegalArgumentException(IllegalArgumentException ex) {
        ExceptionResponse exceptionResponse = exceptionResponse(HttpStatus.PAYLOAD_TOO_LARGE, "Validation error", ex.getMessage());

        return exceptionResponse;
    }


    private ExceptionResponse exceptionResponse(HttpStatus httpStatus, String error, String message) {
        return ExceptionResponse.builder()
                .httpStatus(httpStatus.value())
                .error(error)
                .description(message)
                .build();
    }

    private List<Map<String, Object>> toList(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(fieldError -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put(fieldError.getField(), fieldError.getDefaultMessage());
                    map.put("currentValue", fieldError.getRejectedValue());

                    return map;
                })
                .collect(Collectors.toList());
    }

}
