package com.acme.transferator.infra.rest.contract;

import com.acme.transferator.infra.rest.model.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

@Api
public interface AccountController {

    @ApiOperation(value = "Get balance of user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 204, message = "User not found", response = ApiError.class)
    })
    ResponseEntity<String> getBalance(String userId);
}