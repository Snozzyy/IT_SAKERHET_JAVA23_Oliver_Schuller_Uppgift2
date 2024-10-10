package se.gritacademy.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.gritacademy.server.model.MessageModel;
import se.gritacademy.server.model.UserMessageModel;
import se.gritacademy.server.model.UserModel;
import se.gritacademy.server.repository.MessageRepository;
import se.gritacademy.server.repository.UserMessageRepository;
import se.gritacademy.server.repository.UserRepository;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMessageRepository userMessageRepository;

    public List<String> findMessageByUser(String email, String password) {
        return messageRepository.findByEmailAndPassword(email, password);
    }

    // Saves a new message to the database and then creates a relation between user and the message
    public void saveMessage(String message, String email, String password) {
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message);
        messageModel = messageRepository.save(messageModel);

        // Get user_id by JWT instead
        UserModel user = userRepository.findByEmailAndPassword(email, password);

        UserMessageModel userMessageModel = new UserMessageModel();
        userMessageModel.setUser(user);
        userMessageModel.setMessage(messageModel);
        userMessageRepository.save(userMessageModel);
    }
}
