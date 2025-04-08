package com.stacs.cs5031.p3.server.model.UserTest;

import com.stacs.cs5031.p3.server.model.User.User;
import org.junit.jupiter.api.BeforeEach;

public class UserTest {
    private User user;

    @BeforeEach
    void setup() {
        user = new User("Alice Bob", "alicebob", "qwerty");
    }

}