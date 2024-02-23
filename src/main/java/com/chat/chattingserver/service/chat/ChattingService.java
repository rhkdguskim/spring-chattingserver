package com.chat.chattingserver.service.chat;

import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
public class ChattingService {

    private final HashMap<String, Participant> participants;
    private final RoomManagerService roomManagerService;

    public ChattingService()
    {
        roomManagerService = new RoomManagerService();
        participants = new HashMap<>();
    }
}
