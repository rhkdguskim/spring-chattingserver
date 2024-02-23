package com.chat.chattingserver.repository;

import com.chat.chattingserver.common.aop.annotation.UserRole;
import com.chat.chattingserver.domain.Friend;
import com.chat.chattingserver.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FriendRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(FriendRepositoryTest.class);


    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;

    @Test
    @DisplayName("addFriend")
    public void addFriend()
    {
        User user = new User();
        user.setUserRole(UserRole.NORMAL);
        user.setUserId("test_account");
        user.setName("test_user_name");
        user.setPassword("test_user_password");
        User createdUser = userRepository.save(user);

        User user2 = new User();
        user2.setUserRole(UserRole.NORMAL);
        user2.setUserId("test_account2");
        user2.setName("test_user_name2");
        user2.setPassword("test_user_password");
        User createdUser2 = userRepository.save(user2);

        Friend friend = new Friend();
        friend.setUser(createdUser);
        friend.setFriend_id(createdUser2.getId());
        friend.setFriend_name(createdUser2.getName());
        assertThat(friendRepository.save(friend)).isEqualTo(friend);

        Friend friend2 = new Friend();
        friend2.setUser(createdUser2);
        friend2.setFriend_id(createdUser.getId());
        friend2.setFriend_name(createdUser.getName());
        assertThat(friendRepository.save(friend2)).isEqualTo(friend2);

        List<User> friends1 = friendRepository.getUserFriends(user.getUserId()).orElse(null);
        List<User> friends2 = friendRepository.getUserFriends(user2.getUserId()).orElse(null);


        friends1.forEach(u -> {
            assertThat(u.getId()).isEqualTo(createdUser2.getId());
            logger.info(u.toString());
        });

        friends2.forEach(u -> {
            assertThat(u.getId()).isEqualTo(createdUser.getId());
            logger.info(u.toString());
        });
    }
}
