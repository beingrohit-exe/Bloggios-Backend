package com.securityModule.authServer.Exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class AuthorizationException extends RuntimeException {
    private HttpStatus status;
    private String message;

    private String errorType;

    public AuthorizationException(HttpStatus status, String message, String errorType) {
        super();
        this.status = status;
        this.message = message;
        this.errorType = errorType;
    }
}
