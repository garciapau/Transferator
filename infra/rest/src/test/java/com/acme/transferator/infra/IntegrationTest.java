package com.acme.transferator.infra;

import com.acme.transferator.application.repository.UserRepository;
import com.acme.transferator.application.usecase.AddUser;
import com.acme.transferator.application.usecase.GetBalanceByUser;
import com.acme.transferator.application.usecase.TransferMoney;
import com.acme.transferator.infra.repository.InMemoryUserRepository;
import com.acme.transferator.infra.rest.contract.model.AddUserPostRequest;
import com.acme.transferator.infra.rest.contract.model.TransferPostRequest;
import com.acme.transferator.infra.rest.controller.AccountRestController;
import com.acme.transferator.infra.rest.handler.RestExceptionHandler;
import com.acme.transferator.infra.rest.util.JsonUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TransferatorApp.class)
public class IntegrationTest {
    private static final String USERS_RESOURCE = "/users";
    private static final String TRANSFERS_RESOURCE = "/transfers";
    private static final String SENDER_ID = "userA";
    private static final String RECEIVER_ID = "userB";
    private MockMvc mockMvc;


    @Before
    public void setUp() {
        UserRepository userRepository = new InMemoryUserRepository();
        GetBalanceByUser getBalanceByUser = new GetBalanceByUser(userRepository);
        AddUser addUser = new AddUser(userRepository);
        TransferMoney transferMoney = new TransferMoney(userRepository);
        AccountRestController accountController = new AccountRestController(getBalanceByUser, transferMoney, addUser);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void whenTransferFromUserAToUserB_shouldUpdateBothBalances() throws Exception {
        AddUserPostRequest addUserPostRequest1 = buildAddUserPostRequest(SENDER_ID, "100");
        callPostMethod(USERS_RESOURCE, JsonUtils.toJson(addUserPostRequest1));
        AddUserPostRequest addUserPostRequest2 = buildAddUserPostRequest(RECEIVER_ID, "100");
        callPostMethod(USERS_RESOURCE, JsonUtils.toJson(addUserPostRequest2));
        TransferPostRequest transferPostRequest = buildTransferPostRequest(SENDER_ID, RECEIVER_ID, "50");
        callPostMethod(TRANSFERS_RESOURCE, JsonUtils.toJson(transferPostRequest));
        checkUserHasBalance(SENDER_ID, 50);
        checkUserHasBalance(RECEIVER_ID, 150);

    }

    @Test
    public void whenTransferFromUserAToUserB_shouldReturnErrorNoFunds() throws Exception {
        AddUserPostRequest addUserPostRequest1 = buildAddUserPostRequest(SENDER_ID, "10");
        callPostMethod(USERS_RESOURCE, JsonUtils.toJson(addUserPostRequest1));
        AddUserPostRequest addUserPostRequest2 = buildAddUserPostRequest(RECEIVER_ID, "100");
        callPostMethod(USERS_RESOURCE, JsonUtils.toJson(addUserPostRequest2));
        TransferPostRequest transferPostRequest = buildTransferPostRequest(SENDER_ID, RECEIVER_ID, "50");
        checkUserHasNotEnoughFunds(TRANSFERS_RESOURCE, JsonUtils.toJson(transferPostRequest));
    }

    private void checkUserHasNotEnoughFunds(String resource, String request) throws Exception {
        mockMvc.perform(post(resource)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    private void checkUserHasBalance(String userA, int i) throws Exception {
        mockMvc.perform(get(String.format("/users/%s/balance", userA))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(i)));
    }

    private TransferPostRequest buildTransferPostRequest(String sender, String receiver, String amount) {
        return new TransferPostRequest.Builder()
                    .setSender(sender)
                    .setReceiver(receiver)
                    .setAmount(amount)
                    .build();
    }

    private void callPostMethod(String resource, String request) throws Exception {
        mockMvc.perform(post(resource)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private AddUserPostRequest buildAddUserPostRequest(String userA, String initialBalance) {
        return new AddUserPostRequest.Builder()
                .setIdentifier(userA)
                .setInitialBalance(initialBalance)
                .build();
    }
}
