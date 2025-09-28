package dev.sudu.userservicesept28.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class ApiResponse<T> {
    private String message;
    private HttpStatusCode code;
    private T data;
    private Long timestamp;

    public ApiResponse(String message, HttpStatusCode code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }
}
