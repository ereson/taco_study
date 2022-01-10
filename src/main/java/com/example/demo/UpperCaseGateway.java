package com.example.demo;


import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

@Component
@MessagingGateway(defaultRequestChannel = "inChannel", defaultReplyChannel = "outChannel")
public interface UpperCaseGateway {

    String Uppercase(String in);
}
