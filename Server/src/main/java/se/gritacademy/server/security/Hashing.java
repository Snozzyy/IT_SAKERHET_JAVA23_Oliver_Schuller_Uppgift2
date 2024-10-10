package se.gritacademy.server.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Hashing {

    public static String hashPassword(String password){
        return BCrypt.hashpw(password, "$2a$10$k8A2IhyHsg5d/AJxh3JISe");
    }
}
