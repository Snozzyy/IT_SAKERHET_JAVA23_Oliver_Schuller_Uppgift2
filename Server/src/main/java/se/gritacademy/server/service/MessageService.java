package se.gritacademy.server.service;

import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.gritacademy.server.model.MessageModel;
import se.gritacademy.server.model.UserMessageModel;
import se.gritacademy.server.model.UserModel;
import se.gritacademy.server.repository.MessageRepository;
import se.gritacademy.server.repository.UserMessageRepository;
import se.gritacademy.server.repository.UserRepository;
import se.gritacademy.server.security.Auth;
import se.gritacademy.server.security.Encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMessageRepository userMessageRepository;

    public List<String> findMessageByUser(String token) throws ParseException, JOSEException {
        return messageRepository.findByUserId(Auth.verifyJWT(token));
    }

    // Saves a new message to the database and then creates a relation between user and the message
    public String saveMessage(String message, String token) throws ParseException, JOSEException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        // Convert key to string
        SecretKey key = Encryption.generateKey();
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        message = Encryption.encryptMessage(message, key);

        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message);
        messageModel = messageRepository.save(messageModel);

        Optional<UserModel> user = userRepository.findById(Auth.verifyJWT(token));

        if (user.isPresent()) {
            UserMessageModel userMessageModel = new UserMessageModel();
            userMessageModel.setUser(userRepository.findById(Auth.verifyJWT(token)).get());
            userMessageModel.setMessage(messageModel);
            userMessageRepository.save(userMessageModel);

            return "Save the following key to decrypt message: " + encodedKey;
        } else {
            return "Log in before saving messages";
        }
    }

    public String decryptMessage(String message, String key) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return Encryption.decryptMessage(message, key);
    }
}
