package com.userModule.registrationService.Payloads;

import com.userModule.registrationService.Constants.RegisterConstants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Author : Rohit Parihar
 * Date : 11/24/2022
 * Time : 3:52 PM
 * Class : registerRequest
 * Project : Bloggios-Backend
 */

@Getter
@Setter
@NoArgsConstructor
public class registerRequest {

    @NotBlank(message = RegisterConstants.NAME_VALIDATION)
    @Size(min = 2, max = 40, message = RegisterConstants.NAME_VALIDATION)
    private String name;

    @NotBlank(message = RegisterConstants.EMAIL_VALIDATION)
    @Size(min = 5, max = 40, message = RegisterConstants.EMAIL_VALIDATION)
    @Email(message = RegisterConstants.EMAIL_VALIDATION)
    private String email;

    @NotBlank(message = RegisterConstants.USERNAME_VALIDATION)
    @Size(min = 2, max = 20,message = RegisterConstants.USERNAME_VALIDATION)
    private String username;

    @NotBlank(message = RegisterConstants.PASSWORD_VALIDATION)
    @Size(min = 8, message = RegisterConstants.PASSWORD_VALIDATION)
    private String password;

}
