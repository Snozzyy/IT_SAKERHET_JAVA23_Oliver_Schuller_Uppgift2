package se.gritacademy.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.gritacademy.server.service.MessageService;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping(value = "/message/read")
    public List<String> readMessage(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {

        return messageService.findMessageByUser(email, password);
    }

    @PostMapping(value = "/message/write")
    public void writeMessage(@RequestParam(value = "message") String message,
                             @RequestParam(value = "email") String email,
                             @RequestParam(value = "password") String password){

        messageService.saveMessage(message, email, password);
    }
}
