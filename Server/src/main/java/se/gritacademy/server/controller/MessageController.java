package se.gritacademy.server.controller;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.gritacademy.server.service.MessageService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.List;

@RestController
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping(value = "/message/my-messages")
    public List<String> readMessage(
            @RequestParam(value = "token") String token) throws ParseException, JOSEException {

        return messageService.findMessageByUser(token);
    }

    @PostMapping(value = "/message/write")
    public String writeMessage(@RequestParam(value = "message") String message,
                             @RequestParam(value = "token") String token) throws ParseException, JOSEException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {

        return messageService.saveMessage(message, token);
    }

    @GetMapping(value = "/message/decrypt")
    public String decryptMessage(@RequestParam(value = "message") String message, @RequestParam(value = "key") String key) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return messageService.decryptMessage(message, key);
    }
}
