package com.chat.chattingserver.domain;


import com.chat.chattingserver.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name="room")
@Getter
@Setter
@ToString
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="owner_id")
    private long ownerId;

    @Column(name="room_name")
    private String roomName;

    @Column(name="last_chat")
    private String lastChat;

    @OneToMany(mappedBy = "room")
    private List<Participant> participants;

    @OneToMany(mappedBy = "room")
    private List<Chat> chats;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RoomType type = RoomType.INDIVIDUAL;
}
