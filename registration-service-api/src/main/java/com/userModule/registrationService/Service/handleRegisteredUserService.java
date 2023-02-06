package com.userModule.registrationService.Service;

import com.userModule.registrationService.Payloads.ApiResponse;
import com.userModule.registrationService.Payloads.assignResponse;
import com.userModule.registrationService.Payloads.registerResponse;

import java.util.List;

/**
 * Author : Rohit Parihar
 * Date : 12/30/2022
 * Time : 2:13 AM
 * Class : handleRegisteredUserService
 * Project : Bloggios-Backend
 */

public interface handleRegisteredUserService {
    ApiResponse lockAccount(String userId);
    registerResponse deleteTemporary(String userId);
    registerResponse deletePermanent(String userId);
    assignResponse assignRole(List<String> roleId, String userId, String currentUser);
}
