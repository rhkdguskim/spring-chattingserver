package com.chat.chattingserver.domain;


import com.chat.chattingserver.common.aop.annotation.ChatType;
import com.chat.chattingserver.common.aop.annotation.RoomType;
import com.chat.chattingserver.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="room")
@Getter
@Setter
public class Room extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="owner_id")
    private long ownerid;

    @Column(name="room_name")
    private String roomname;

    @Column(name="last_chat")
    private String lastchat;

    @OneToMany
    private List<Participant> participants;

    @OneToMany
    private List<Chat> chats;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RoomType type = RoomType.INDIVIDUAL;
}
