package com.chat.chattingserver.repository;
import com.chat.chattingserver.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("select r from Room r join Participant p on p.room.id = r.id where p.user.userId = :userId")
    List<Room> findRoomByUsers(@Param("userId") String userId);
}
