package se.gritacademy.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.gritacademy.server.model.MessageModel;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageModel, Long> {

    @Query(value = "SELECT m.message_content FROM message m " +
            "JOIN user_message um ON m.message_id = um.message_id " +
            "JOIN user u ON um.user_id = u.user_id " +
            "WHERE u.email = :email AND u.password = :password", nativeQuery = true)
    List<String> findByEmailAndPassword(String email, String password);
}

