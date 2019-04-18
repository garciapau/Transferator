package com.acme.transferator.infra.rest.contract;

import com.acme.transferator.application.exception.NotEnoughFundsException;
import com.acme.transferator.application.exception.UserNotFoundException;
import com.acme.transferator.infra.rest.contract.model.TransferPostRequest;
import com.acme.transferator.infra.rest.model.ApiError;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

@Api
public interface AccountController {

    @ApiOperation(value = "Get balance of user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = String.class),
            @ApiResponse(code = 204, message = "User not found", response = ApiError.class)
    })
    ResponseEntity<BigDecimal> getBalance(String userId) throws UserNotFoundException;

    @ApiOperation(value = "Transfer money between two users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 204, message = "User not found", response = ApiError.class),
            @ApiResponse(code = 409, message = "Sender does not have enough funds", response = ApiError.class)
    })
    ResponseEntity transferMoney(TransferPostRequest transferPostRequest) throws UserNotFoundException, NotEnoughFundsException;
}
