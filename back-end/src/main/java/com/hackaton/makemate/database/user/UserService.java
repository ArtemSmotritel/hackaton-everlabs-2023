package com.hackaton.makemate.database.user;

import com.hackaton.makemate.domain.user.User;

import java.util.List;

public interface UserService {
    List<User> matchingUsers(Long userId);
}
