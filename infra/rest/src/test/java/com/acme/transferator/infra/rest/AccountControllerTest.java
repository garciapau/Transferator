package com.acme.transferator.infra.rest;

import com.acme.transferator.application.exception.UserNotFoundException;
import com.acme.transferator.application.usecase.GetBalanceByUser;
import com.acme.transferator.infra.rest.controller.AccountRestController;
import com.acme.transferator.infra.rest.handler.RestExceptionHandler;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest {
    private MockMvc mockMvc;

    @Mock
    GetBalanceByUser getBalanceByUser;

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
        when(getBalanceByUser.execute(any(String.class))).thenThrow(new UserNotFoundException());

        mockMvc.perform(get(String.format("/users/%s/balance", "NonExistingUser"))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message", is("User not found")));
    }
}
