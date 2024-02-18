package com.chat.chattingserver.domain;


import com.chat.chattingserver.common.aop.annotation.ChatType;
import com.chat.chattingserver.common.aop.annotation.UserRole;
import com.chat.chattingserver.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="chat")
@Getter
@Setter
public class Chat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String message;

    private long notReadChat;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ChatType type = ChatType.TEXT;
}
