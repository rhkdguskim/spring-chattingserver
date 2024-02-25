package com.chat.chattingserver.domain;


import com.chat.chattingserver.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="chat")
@Getter
@Setter
@ToString
public class Chat extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name ="message")
    private String message;

    @Column(name ="not_read_chat")
    private long notreadchat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ChatType type = ChatType.TEXT;
}
