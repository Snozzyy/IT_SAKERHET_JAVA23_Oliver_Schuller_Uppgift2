package se.gritacademy.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import se.gritacademy.server.model.UserModel;
import se.gritacademy.server.repository.UserRepository;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserModel saveUser(String email, String password) {
        UserModel user = new UserModel();
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }

    public UserModel findByEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }
}
