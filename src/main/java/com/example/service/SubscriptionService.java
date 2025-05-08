package com.example.service;

import com.example.model.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    void addSubscription(Long userId, SubscriptionDto dto);
    List<SubscriptionDto> getSubscriptions(Long userId);
    void deleteSubscription(Long userId, Long subId);
    List<String> getTopSubscriptions();
}
