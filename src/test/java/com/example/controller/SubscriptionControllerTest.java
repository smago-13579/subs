package com.example.controller;

import com.example.entity.Subscription;
import com.example.model.ResponseData;
import com.example.model.SubscriptionDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubscriptionControllerTest extends ParentTest {

    @Test
    void addSubscription200Test() {
        String path = "/users/%d/subscriptions".formatted(users.get(1).getId());
        MvcResult mvcResult = performPost(new SubscriptionDto("YouTube"), path);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void addSubscription400Test() {
        String path = "/users/%d/subscriptions".formatted(users.get(0).getId());

        MvcResult mvcResult = performPost(new SubscriptionDto("YouTube"), path);
        assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void addSubscription404Test() {
        String path = "/users/%d/subscriptions".formatted(9999);

        MvcResult mvcResult = performPost(new SubscriptionDto("YouTube"), path);
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void getSubscriptionsTest() {
        String path = "/users/%d/subscriptions".formatted(users.get(0).getId());

        MvcResult mvcResult = performGet(path);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        ResponseData<List<SubscriptionDto>> body = readValue(mvcResult, ResponseData.class);

        assertEquals(3, body.data().size());
    }

    @Test
    void deleteSubscriptionTest() {
        String path = "/users/%d/subscriptions/%d".formatted(users.get(0).getId(), subscriptions.get(0).getId());

        MvcResult mvcResult = performDelete(path);
        assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus());

        var data = subscriptionRepository.findAllByUserAndActiveTrue(users.get(0));
        assertEquals(2, data.size());
    }

    @Test
    void getTopSubscriptionsTest() {
        subscriptionRepository.save(new Subscription("YouTube", users.get(1)));
        String path = "/subscriptions/top";

        MvcResult mvcResult = performGet(path);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }
}
