package com.example.controller;

import com.example.model.ResponseData;
import com.example.model.SubscriptionDto;
import com.example.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final HttpHeaders headers;

    @PostMapping("/users/{id}/subscriptions")
    ResponseEntity<?> addSubscription(@PathVariable(name = "id") Long userId,
                                      @RequestBody @Validated SubscriptionDto dto) {
        subscriptionService.addSubscription(userId, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/{id}/subscriptions")
    ResponseEntity<?> getSubscriptions(@PathVariable(name = "id") Long userId) {
        List<SubscriptionDto> data = subscriptionService.getSubscriptions(userId);
        return new ResponseEntity<>(new ResponseData<>(data), headers, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}/subscriptions/{subId}")
    ResponseEntity<?> deleteSubscription(@PathVariable(name = "id") Long userId,
                                         @PathVariable Long subId) {
        subscriptionService.deleteSubscription(userId, subId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    ResponseEntity<?> getTopSubscriptions() {
        List<String> data = subscriptionService.getTopSubscriptions();
        return new ResponseEntity<>(new ResponseData<>(data), headers, HttpStatus.OK);
    }
}
