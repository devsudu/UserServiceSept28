package dev.sudu.userservicesept28.advices;

import dev.sudu.userservicesept28.dtos.ApiResponse;
import dev.sudu.userservicesept28.models.Status;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Return true to apply this advice to all responses.
        // You can add conditions here to apply it only to specific controllers or types.
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // If the body is already an ApiResponse, return it directly.
        if (body instanceof ApiResponse<?>) {
//            return body;
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(body);

        }

        HttpStatus httpStatus = switch (request.getMethod().toString()) {
            case "GET":
                yield HttpStatus.OK; // 200
            case "POST":
                yield HttpStatus.CREATED; // 201
            case "PUT":
                yield HttpStatus.ACCEPTED; // 202
            case "PATCH":
                yield HttpStatus.ACCEPTED; // 202
            case "DELETE":
                yield HttpStatus.NO_CONTENT; // 204
            default:
                yield HttpStatus.INTERNAL_SERVER_ERROR;
        };

        // Wrap the original response body in your ApiResponse.
        ApiResponse<Object> apiResponse = new ApiResponse<>(Status.SUCCESS.toString(), httpStatus, body);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return ResponseEntity.status(httpStatus).headers(headers).body(apiResponse);
    }
}
