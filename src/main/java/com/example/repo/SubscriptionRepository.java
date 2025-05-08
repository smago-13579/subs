package com.example.repo;

import com.example.entity.Subscription;
import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("""
            SELECT s FROM Subscription s
            JOIN FETCH s.user u
            WHERE u.id = :userId AND upper(s.mediaService) = upper(:mediaService)
            AND s.active = true""")
    Optional<Subscription> findByUserId(Long userId, String mediaService);

    List<Subscription> findAllByUserAndActiveTrue(User user);
    Optional<Subscription> findByIdAndUserAndActiveTrue(Long id, User user);
}
