package ao.it.chandsoft.whatsapp.controller.handler;

import ao.it.chandsoft.whatsapp.dto.ExceptionResponse;
import ao.it.chandsoft.whatsapp.dto.WhatsAppErrorResponse;
import ao.it.chandsoft.whatsapp.exception.MediaFileException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(FeignException.FeignClientException.class)
    ResponseEntity<ExceptionResponse> feignException(FeignException.FeignClientException ex) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        WhatsAppErrorResponse errorResponse = null;
        String message = null;

        if(!ex.contentUTF8().isBlank()) {
            errorResponse = objectMapper.readValue(ex.contentUTF8(), WhatsAppErrorResponse.class);
            message = errorResponse.error().message();
        }

        ExceptionResponse exceptionResponse =  switch (ex.status()) {
            case 400  -> exceptionResponse(HttpStatus.BAD_REQUEST, "Bad request", extractMessage(message));
            //case 400  -> exceptionResponse(HttpStatus.BAD_REQUEST, "Bad request", errorResponse.substring(0, errorResponse.lastIndexOf(". Please")));
            case 401 -> exceptionResponse(HttpStatus.UNAUTHORIZED, "Authentication failed",  "Invalid WhatsApp credentials");
            case 403 -> exceptionResponse(HttpStatus.FORBIDDEN, "Denied access", message);
            default -> exceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal error server", message);
        };

        return ResponseEntity.status(exceptionResponse.getHttpStatus()).body(exceptionResponse);
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

    private String extractMessage(String message) {
        if(message.contains(". Please")) {
            return message.substring(0, message.lastIndexOf(". Please"));
        }

        if (message.matches("\\(#\\d+\\) .+")) {
            return message.substring(message.indexOf(")") + 1).trim();
        }

        return message;
    }

}
