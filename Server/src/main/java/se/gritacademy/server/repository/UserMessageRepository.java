package se.gritacademy.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.gritacademy.server.model.UserMessageModel;

public interface UserMessageRepository extends JpaRepository<UserMessageModel, Long> {

}
