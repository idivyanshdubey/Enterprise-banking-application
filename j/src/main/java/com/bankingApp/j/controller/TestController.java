package com.bankingApp.j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.j.service.ActiveMQSender;

@RestController
public class TestController {
    
    @Autowired
    private ActiveMQSender activeMQSender;

    @GetMapping("/send")
    public String sendMessage() {
        activeMQSender.sendMessage("bankQueue", "Transaction processed!");
        return "Message sent!";
    }
}




