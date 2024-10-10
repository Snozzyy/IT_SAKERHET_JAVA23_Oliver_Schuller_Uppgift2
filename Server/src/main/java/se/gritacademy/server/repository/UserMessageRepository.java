package se.gritacademy.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.gritacademy.server.model.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {

}
