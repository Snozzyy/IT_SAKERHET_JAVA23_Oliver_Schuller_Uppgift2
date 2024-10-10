package se.gritacademy.server.service;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import se.gritacademy.server.model.UserModel;
import se.gritacademy.server.repository.UserRepository;
import se.gritacademy.server.security.Auth;
import se.gritacademy.server.security.Hashing;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public UserModel saveUser(String email, String password) {
        UserModel user = new UserModel();
        user.setEmail(email);
        user.setPassword(Hashing.hashPassword(password));

        return userRepository.save(user);
    }

    public String findByEmailAndPassword(String email, String password) throws JOSEException, ParseException {

        UserModel userModel = userRepository.findByEmailAndPassword(email, Hashing.hashPassword(password));

        if (userModel != null) {
            return Auth.generateJWT(userModel.getId());
        } else {
            // Error
            return "No user found";
        }
    }
}
