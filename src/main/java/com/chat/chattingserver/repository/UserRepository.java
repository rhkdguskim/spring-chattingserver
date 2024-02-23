package com.chat.chattingserver.repository;
import com.chat.chattingserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userid);
    Optional<User> findByName(String name);

    boolean existsUserByUserId(String userid);
}
