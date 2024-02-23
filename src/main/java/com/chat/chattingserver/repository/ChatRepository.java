package com.chat.chattingserver.repository;
import com.chat.chattingserver.domain.Chat;
import com.chat.chattingserver.dto.ChatDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("select new com.chat.chattingserver.dto.ChatDto$ChatMessageResponse(c.id, c.message, c.user.name, c.room.roomName, c.createdAt, c.updatedAt) " +
            "from Chat c where c.room.id = :roomId and c.id < :cursor")
    Slice<ChatDto.ChatMessageResponse> getChattings(@Param("roomId") Long roomId,
                                                    @Param("cursor") Long cursor,
                                                    Pageable pageable);
}
