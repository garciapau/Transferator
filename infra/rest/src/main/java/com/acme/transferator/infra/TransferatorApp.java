package com.acme.transferator.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "com.acme.transferator.infra.rest.controller",
        "com.acme.transferator.infra.rest.config",
        "com.acme.transferator.infra.rest.handler"})
public class TransferatorApp {

    public static void main(String[] args) {
        SpringApplication.run(TransferatorApp.class, args);
    }
}
