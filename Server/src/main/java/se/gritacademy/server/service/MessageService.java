package se.gritacademy.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.gritacademy.server.model.MessageModel;
import se.gritacademy.server.model.UserMessage;
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

    public List<String> saveMessage(String message, String email, String password) {
        MessageModel messageModel = new MessageModel();
        messageModel.setMessage(message);

        messageModel = messageRepository.save(messageModel);
        Long messageId = messageModel.getMessageId();

        //TODO
        // Use messageId and userId to save relation between message and user

        UserMessage userMessage = new UserMessage();
        UserModel user = userRepository.findByEmailAndPassword(email, password);


        return null;
    }
}
