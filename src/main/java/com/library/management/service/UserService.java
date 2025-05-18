package com.library.management.service;

import com.library.management.model.User;

public interface UserService {
    User registerUser(User user);
    User loginUser(String username, String password);
}
