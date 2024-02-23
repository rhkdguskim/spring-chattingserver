package com.chat.chattingserver.repository;
import com.chat.chattingserver.domain.Friend;
import com.chat.chattingserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select f from Friend f where f.friend_id = :friendId and f.user.userId =:userId")
    Optional<Friend> findFriend(@Param("userId") String userId, @Param("friendId") Long friendId);
    @Modifying
    @Transactional
    @Query("delete from Friend f where f.friend_id = :friendId and f.user.userId =:userId")
    void delete(@Param("userId") String userId, @Param("friendId") Long friendId);

    @Query("select u from User u where u.id in " +
            "(select f.friend_id from Friend f where f.user.userId = :userId)")
    Optional<List<User>> getUserFriends(@Param("userId") String userId);
}
