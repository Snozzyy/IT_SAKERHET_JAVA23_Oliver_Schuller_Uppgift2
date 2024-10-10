package se.gritacademy.server.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user_message")
@Table(name = "user_message")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMessageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private MessageModel message;
}