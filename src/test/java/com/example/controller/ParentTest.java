package com.example.controller;

import com.example.entity.Subscription;
import com.example.entity.User;
import com.example.repo.SubscriptionRepository;
import com.example.repo.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ParentTest extends PSQLContainerTest {
    protected List<User> users;
    protected List<Subscription> subscriptions;

    @BeforeEach
    void setUp() {
        User one = userRepository.save(new User("user-one", "user-one", "user-one"));
        User two = userRepository.save(new User("user-two", "user-two", "user-two"));
        users = List.of(one, two);

        var subOne = subscriptionRepository.save(new Subscription("YouTube", one));
        var subTwo = subscriptionRepository.save(new Subscription("VK Music", one));
        var subThree = subscriptionRepository.save(new Subscription("Netflix", one));
        subscriptions = List.of(subOne, subTwo, subThree);
    }

    @AfterEach
    void tearDown() {
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected SubscriptionRepository subscriptionRepository;

    @Autowired
    protected MockMvc mvc;
    protected final ObjectMapper mapper = new ObjectMapper();


    @SneakyThrows
    protected <T>MvcResult performGet(String path) {
        return mvc.perform(
                get(path)
        ).andDo(print()).andReturn();
    }

    @SneakyThrows
    protected <T>MvcResult performPost(T dto, String path) {
        return mvc.perform(
                post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
        ).andDo(print()).andReturn();
    }

    @SneakyThrows
    protected <T>MvcResult performPut(T dto, String path) {
        return mvc.perform(
                put(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
        ).andDo(print()).andReturn();
    }

    @SneakyThrows
    protected <T>MvcResult performDelete(String path) {
        return mvc.perform(
                delete(path)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andReturn();
    }

    @SneakyThrows
    protected<T> T readValue(MvcResult mvcResult, Class<T> clazz) {
        return mapper.readValue(mvcResult.getResponse().getContentAsString(), clazz);
    }

    @SneakyThrows
    protected<T> List<T> readListValue(String content, Class<T> clazz) {
        return mapper.readValue(content, new TypeReference<List<T>>() {});
    }

    protected<T, R> T convertValue(R obj, Class<T> clazz) {
        return mapper.convertValue(obj, clazz);
    }
}
