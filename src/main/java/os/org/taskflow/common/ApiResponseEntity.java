package os.org.taskflow.common;
import org.springframework.http.HttpStatus;

import lombok.Getter;

import org.springframework.http.HttpHeaders;

import java.time.Instant;

@Getter
public class ApiResponseEntity<T> {
    private Instant timeStamp;
    private boolean success;
    private String error;
    private HttpStatus statusCode;
    private String message;
    private T data;
    private HttpHeaders headers;

    public ApiResponseEntity(Instant timeStamp,boolean success, T data) {
        this.timeStamp = timeStamp;
        this.success = success;
        this.data = data;
    }

    public ApiResponseEntity(Instant timeStamp,boolean success, T data, HttpStatus statusCode) {
        this.timeStamp = timeStamp;
        this.success = success;
        this.data = data;
        this.statusCode = statusCode;
    }

    public ApiResponseEntity(Instant timeStamp,boolean success, T data, String error, HttpStatus statusCode, String message) {
        this.timeStamp = timeStamp;
        this.success = success;
        this.data = data;
        this.error = error;
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiResponseEntity(Instant timeStamp,boolean success, String error, HttpStatus statusCode, String message, T data,
                             HttpHeaders headers) {
        this.timeStamp = timeStamp;
        this.success = success;
        this.error = error;
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.headers = headers;
    }

    public ApiResponseEntity(Instant timeStamp,boolean success, HttpStatus statusCode, T data, HttpHeaders headers) {
        this.timeStamp = timeStamp;
        this.success = success;
        this.statusCode = statusCode;
        this.data = data;
        this.headers = headers;
    }

    public ApiResponseEntity(Instant timeStamp,boolean success, HttpStatus statusCode, String message) {
        this.timeStamp = timeStamp;
        this.success = success;
        this.statusCode = statusCode;
        this.message = message;
    }

    public ApiResponseEntity(boolean success, HttpStatus statusCode, String error, String message) {
        this.timeStamp = timeStamp;
        this.success = success;
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
    }

    public ApiResponseEntity(boolean success, T data, HttpHeaders header, HttpStatus statusCode) {
        this.timeStamp = timeStamp;
        this.success = success;
        this.data = data;
        this.headers = header;
        this.statusCode = statusCode;
    }
}
