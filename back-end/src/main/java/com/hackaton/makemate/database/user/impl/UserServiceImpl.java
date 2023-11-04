package com.hackaton.makemate.database.user.impl;

import com.hackaton.makemate.database.user.UserRepository;
import com.hackaton.makemate.database.user.UserService;
import com.hackaton.makemate.domain.exception.BadRequestException;
import com.hackaton.makemate.domain.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> matchingUsers(Long userId) {
        User user = fetchUserById(userId);

        List<User> users = userRepository.findAll();

        return matchAlgorithm(user, users);
    }

    private User fetchUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(String.format("User with id %s doesn't exist", userId)));
    }

    private List<User> matchAlgorithm(User sender, List<User> targets) {
        return new ArrayList<>(targets);
    }
}
