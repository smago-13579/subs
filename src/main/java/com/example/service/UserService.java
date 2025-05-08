package com.example.service;

import com.example.model.UserDto;

public interface UserService {
    UserDto createUser(UserDto dto);
    UserDto getUser(Long id);
    void updateUser(Long id, UserDto dto);
    void deleteUser(Long id);
}
