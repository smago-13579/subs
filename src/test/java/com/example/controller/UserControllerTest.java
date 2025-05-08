package com.example.controller;

import com.example.model.UserDto;
import com.example.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest extends ParentTest {

    @Test
    void getUser404Test() {
        String path = "/users/99999";

        MvcResult mvcResult = performGet(path);
        assertEquals(HttpStatus.NOT_FOUND.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void getUser200Test() {
        for (User user : users) {
            String path = "/users/" + user.getId();

            MvcResult mvcResult = performGet(path);
            assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        }
    }

    @ParameterizedTest
    @MethodSource("createUserData")
    void createUser(UserDto dto, HttpStatus expectedStatus) {
        String path = "/users";

        MvcResult mvcResult = performPost(dto, path);
        assertEquals(expectedStatus.value(), mvcResult.getResponse().getStatus());
    }

    static Stream<Arguments> createUserData() {
        return Stream.of(
                Arguments.of(new UserDto(null, "John-Doe", "John", "Doe", true),
                        HttpStatus.CREATED),
                Arguments.of(new UserDto(null, "John-Doe", "", "Doe", true),
                        HttpStatus.BAD_REQUEST)
        );
    }

    @Test
    void updateUser() {
        String path = "/users/" + users.get(0).getId();
        var dto = new UserDto(null, "John-Doe", "John", "Doe", true);

        MvcResult mvcResult = performPut(dto, path);
        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
    }

    @Test
    void deleteUser() {
        String path = "/users/" + users.get(0).getId();

        MvcResult mvcResult = performDelete(path);
        assertEquals(HttpStatus.NO_CONTENT.value(), mvcResult.getResponse().getStatus());
        assertEquals(0, subscriptionRepository.findAllByUserAndActiveTrue(users.get(0)).size());
    }
}
