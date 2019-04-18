package com.acme.transferator.infra.rest;

import com.acme.transferator.application.domain.StandardAccount;
import com.acme.transferator.application.domain.User;
import com.acme.transferator.application.exception.NotEnoughFundsException;
import com.acme.transferator.application.exception.UserNotFoundException;
import com.acme.transferator.application.usecase.AddUser;
import com.acme.transferator.application.usecase.GetBalanceByUser;
import com.acme.transferator.application.usecase.TransferMoney;
import com.acme.transferator.infra.rest.contract.model.AddUserPostRequest;
import com.acme.transferator.infra.rest.contract.model.TransferPostRequest;
import com.acme.transferator.infra.rest.controller.AccountRestController;
import com.acme.transferator.infra.rest.handler.RestExceptionHandler;
import com.acme.transferator.infra.rest.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest {
    private MockMvc mockMvc;

    @Mock
    AddUser addUser;

    @Mock
    GetBalanceByUser getBalanceByUser;

    @Mock
    TransferMoney transferMoney;

    @InjectMocks
    private AccountRestController accountController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void whenCallToGetBalance_shouldReturnHttpStatusOk() throws Exception, UserNotFoundException {
        when(getBalanceByUser.execute(any(String.class))).thenReturn(new BigDecimal("0"));

        mockMvc.perform(get(String.format("/users/%s/balance", "userA"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallToGetBalanceOfNonExistentUser_shouldReturnHttpStatusConflict() throws Exception, UserNotFoundException {
        when(getBalanceByUser.execute(any(String.class))).thenThrow(new UserNotFoundException("UserA"));

        mockMvc.perform(get(String.format("/users/%s/balance", "NonExistingUser"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("User UserA not found")));
    }

    @Test
    public void whenCallToTransferMoney_shouldReturnHttpStatusOk() throws Exception, UserNotFoundException, NotEnoughFundsException {
        when(transferMoney.execute(any(String.class), any(String.class), any(String.class))).thenReturn(true);
        TransferPostRequest transferPostRequest = new TransferPostRequest.Builder()
                .setSender("userA")
                .setReceiver("userB")
                .setAmount("100")
                .build();
        mockMvc.perform(post("/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(transferPostRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenCallToTransferMoneyWithoutEnoughFunds_shouldReturnConflict() throws Exception, UserNotFoundException, NotEnoughFundsException {
        when(transferMoney.execute(any(String.class), any(String.class), any(String.class)))
                .thenThrow(new NotEnoughFundsException(buildTestUserWithBalanceZero("UserA")));
        TransferPostRequest transferPostRequest = new TransferPostRequest.Builder()
                .setSender("userA")
                .setReceiver("userB")
                .setAmount("100")
                .build();
        mockMvc.perform(post("/transfers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(transferPostRequest)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void whenCallAddUser_shouldReturnHttpStatusOk() throws Exception {
        when(addUser.execute(any(String.class), any(String.class))).thenReturn(buildTestUserWithBalanceZero("UserA"));
        AddUserPostRequest addUserPostRequest = new AddUserPostRequest.Builder()
                .setIdentifier("userA")
                .setInitialBalance("0")
                .build();
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtils.toJson(addUserPostRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private User buildTestUserWithBalanceZero(String identifier) {
        return new User.Builder()
                .setIdentifier(identifier)
                .setAccount(new StandardAccount.Builder()
                        .setCurrentBalance(new BigDecimal("0"))
                        .build())
                .build();
    }
}
