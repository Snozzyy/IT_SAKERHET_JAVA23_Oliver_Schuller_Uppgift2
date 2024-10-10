package se.gritacademy.server.controller;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.gritacademy.server.model.UserModel;
import se.gritacademy.server.service.UserService;

import java.text.ParseException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/register")
    public UserModel register(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password
    ){

        return userService.saveUser(email, password);
    }

    @GetMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) throws JOSEException, ParseException {

        return new ResponseEntity<>(userService.findByEmailAndPassword(email, password), HttpStatus.OK);
    }
}
