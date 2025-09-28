package dev.sudu.userservicesept28.advices;

import dev.sudu.userservicesept28.dtos.ApiResponse;
import dev.sudu.userservicesept28.models.Status;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> exceptionHandler(Exception e) {
        return new ApiResponse<>(Status.ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
