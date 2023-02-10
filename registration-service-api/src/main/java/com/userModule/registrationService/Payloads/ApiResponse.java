package com.userModule.registrationService.Payloads;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author : Rohit Parihar
 * Date : 11/24/2022
 * Time : 4:11 PM
 * Class : ApiResponse
 * Project : Bloggios-Backend
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    String message;
    Boolean success;

    public ApiResponse(String message) {
        this.message = message;
    }
}
