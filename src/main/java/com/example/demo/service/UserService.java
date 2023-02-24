package com.example.demo.service;


import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(Long userId);

    List<User> getAllUsers();


    void deleteUser(Long userId);
}
