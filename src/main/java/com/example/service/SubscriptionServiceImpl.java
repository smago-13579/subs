package com.example.service;

import com.example.entity.Subscription;
import com.example.entity.User;
import com.example.exception.NotFoundException;
import com.example.model.SubscriptionDto;
import com.example.repo.CommonRepository;
import com.example.repo.SubscriptionRepository;
import com.example.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final CommonRepository commonRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addSubscription(Long userId, SubscriptionDto dto) {
        log.info("The SubscriptionService is called - addSubscription. UserId: {}", userId);

        subscriptionRepository.findByUserId(userId, dto.getMediaService())
                .ifPresent(it -> { throw new IllegalArgumentException("The subscription already exists");});

        User user = userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new NotFoundException("The user does not exist"));
        Subscription subscription = new Subscription(dto.getMediaService(), user);
        subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public List<SubscriptionDto> getSubscriptions(Long userId) {
        log.info("The SubscriptionService is called - getSubscriptions. UserId: {}", userId);

        User user = userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new NotFoundException("The user does not exist"));

        return subscriptionRepository.findAllByUserAndActiveTrue(user)
                .stream()
                .map(it -> new SubscriptionDto(it.getId(), it.getMediaService()))
                .toList();
    }

    @Override
    @Transactional
    public void deleteSubscription(Long userId, Long subId) {
        log.info("The SubscriptionService is called - deleteSubscription. UserId: {}, subId: {}", userId, subId);

        User user = userRepository.findByIdAndActiveTrue(userId)
                .orElseThrow(() -> new NotFoundException("The user does not exist"));

        Subscription subscription = subscriptionRepository.findByIdAndUserAndActiveTrue(subId, user)
                .orElseThrow(() -> new NotFoundException("The subscription was not found."));
        subscription.setActive(false);
        subscription.setDisabledAt(LocalDateTime.now());
        subscriptionRepository.save(subscription);
    }

    @Override
    public List<String> getTopSubscriptions() {
        log.info("The SubscriptionService is called - getTopSubscriptions");
        return commonRepository.findTopThree();
    }
}
