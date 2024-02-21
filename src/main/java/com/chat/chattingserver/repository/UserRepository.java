package com.chat.chattingserver.repository;
import com.chat.chattingserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserid(String userid);
    Optional<User> findByNickname(String nickname);

    boolean existsUserByUserid(String userid);
}
