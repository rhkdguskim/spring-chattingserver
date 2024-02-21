package com.chat.chattingserver.repository;
import com.chat.chattingserver.domain.Friend;
import com.chat.chattingserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    @Query("select u from User u where u.id in " +
            "(select f.id from Friend f where f.friend_id = :userId)")
    Optional<List<User>> getUserFriends(@Param("userId") Long userId);
}
