package com.chat.chattingserver.repository;

import com.chat.chattingserver.domain.UserRole;
import com.chat.chattingserver.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("createUser")
    public void createUser()
    {
        User user = new User();
        user.setUserRole(UserRole.NORMAL);
        user.setUserId("test_account");
        user.setName("test_user_name");
        user.setPassword("test_user_password");
        User createdUser = repository.save(user);

        assertThat(createdUser).isInstanceOf(User.class);
        assertEquals(createdUser.getUsername(), user.getUsername());
        assertEquals(createdUser.getName(), user.getName());
    }

    @Test
    @DisplayName("create100Users")
    public void create100Users()
    {
        List<User> users = new ArrayList<User>();
        for (int i=0; i < 100; i++)
        {
            User user = new User();
            user.setUserRole(UserRole.NORMAL);
            user.setUserId("test_account");
            user.setName("test_user_name");
            user.setPassword("test_user_password");
            User createdUser = repository.save(user);

            assertEquals(createdUser.getUsername(), user.getUsername());
            assertEquals(createdUser.getName(), user.getName());
            users.add(createdUser);
        }
        assertThat(users.size()).isEqualTo(100);
    }
}
