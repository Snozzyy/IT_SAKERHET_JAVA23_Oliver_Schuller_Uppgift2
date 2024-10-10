package se.gritacademy.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "message")
@Table(name = "message")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageModel {

    @Id
    @Column(name = "message_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long messageId;

    @Column(name = "message_content")
    String message;

    @OneToOne(mappedBy = "message")
    private UserMessageModel userMessageModel;
}
