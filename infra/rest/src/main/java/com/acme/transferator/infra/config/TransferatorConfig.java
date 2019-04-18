package com.acme.transferator.infra.config;

import com.acme.transferator.application.repository.UserRepository;
import com.acme.transferator.application.usecase.GetBalanceByUser;
import com.acme.transferator.application.usecase.TransferMoney;
import com.acme.transferator.infra.repository.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransferatorConfig {
    @Bean
    UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

    @Bean
    GetBalanceByUser getBalanceByUser(UserRepository userRepository) {
        return new GetBalanceByUser(userRepository);
    }

    @Bean
    TransferMoney getTransferMoney(UserRepository userRepository) { return new TransferMoney(userRepository); }
}
