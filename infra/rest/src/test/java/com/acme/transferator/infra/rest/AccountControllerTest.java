package com.acme.transferator.infra.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private AccountController accountController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void whenCallToGetBalance_shouldReturnHttpStatosOk() throws Exception {
        mockMvc.perform(get(String.format("/users/%s/balance", "userA"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
