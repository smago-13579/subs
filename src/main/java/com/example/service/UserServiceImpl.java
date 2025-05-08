package com.example.service;

import com.example.model.UserDto;
import com.example.entity.User;
import com.example.exception.NotFoundException;
import com.example.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto createUser(UserDto dto) {
        log.info("The UserService is called - createUser. Username: {}", dto.getUsername());

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("The username is already in use");
        }
        var user = new User(dto.getUsername(), dto.getFirstName(), dto.getLastName());
        userRepository.save(user);
        dto.setId(user.getId());

        return dto;
    }

    @Override
    public UserDto getUser(Long id) {
        log.info("The UserService is called - getUser. Id: {}", id);

        var user = userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("The user does not exist"));

        return new UserDto(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.isActive());
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDto dto) {
        log.info("The UserService is called - updateUser. Id: {}", id);

        var user = userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("The user does not exist"));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info("The UserService is called - deleteUser. Id: {}", id);

        var user = userRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("The user does not exist"));
        user.setActive(false);
        user.getSubscriptions().forEach(subscription -> {
            if (subscription.isActive()) {
                subscription.setActive(false);
                subscription.setDisabledAt(LocalDateTime.now());
            }
        });
        userRepository.save(user);
    }
}
