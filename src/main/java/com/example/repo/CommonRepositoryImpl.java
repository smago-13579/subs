package com.example.repo;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommonRepositoryImpl implements CommonRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<String> findTopThree() {
        return jdbcTemplate.query("""
                SELECT s.media_service, count(*) as cnt
                FROM common.subscriptions s
                WHERE s.is_active = true
                GROUP BY s.media_service
                ORDER BY cnt DESC LIMIT 3
                """, (ResultSet rs, int rowNum) -> rs.getString(1));
    }
}
