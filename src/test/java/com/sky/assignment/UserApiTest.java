package com.sky.assignment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.sky.assignment.model.Project;
import com.sky.assignment.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class UserApiTest {

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private User createUser() throws Exception {
        User user = new User();
        user.setName("me");
        user.setEmail("me@gmail.com");
        user.setPassword("letmein");

        MockHttpServletRequestBuilder request = post("/user")
                .with(httpBasic("admin", "password"))
                .content(toJson(user))
                .contentType(MediaType.APPLICATION_JSON);

        String contentAsString = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(contentAsString, User.class);
    }

    private void updateUser(Integer userId, User user) throws Exception {
        MockHttpServletRequestBuilder req = put("/user/{userId}", userId)
                .with(httpBasic("admin", "password"))
                .content(toJson(user))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(req).andExpect(status().isOk());
    }


    @Test
    void testGetUser() throws Exception {
        User user = createUser();
        Integer userId = user.getId();
        String contentAsString = mockMvc.perform(
                        get("/user/{userId}", userId)
                                .with(httpBasic("admin", "password")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        User retrievedUser = objectMapper.readValue(contentAsString, User.class);
        Assertions.assertEquals(user, retrievedUser);
    }

    @Test
    void testUserNotValid() throws Exception {
        User user = new User();
        user.setName("me");
        user.setEmail("email");
        user.setPassword("letmein");

        MockHttpServletRequestBuilder request = post("/user")
                .with(httpBasic("admin", "password"))
                .content(toJson(user))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }


    @Test
    void testUpdateUser() throws Exception {
        User user = createUser();

        String newName = "New user name";
        user.setName(newName);

        updateUser(user.getId(), user);

        Integer userId = user.getId();
        String contentAsString = mockMvc.perform(
                        get("/user/{userId}", userId)
                                .with(httpBasic("admin", "password")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        User retrievedUser = objectMapper.readValue(contentAsString, User.class);
        Assertions.assertEquals(user, retrievedUser);

    }

    @Test
    void testDeleteUser() throws Exception {
        User user = createUser();

        Integer userId = user.getId();
        MockHttpServletRequestBuilder deleteRequest = delete("/user/{userId}", userId)
                .with(httpBasic("admin", "password"));
        mockMvc.perform(deleteRequest).andExpect(status().isOk());

        MockHttpServletRequestBuilder getUserRequest = get("/user/{userId}", user.getId()).with(httpBasic("admin", "password"));
        mockMvc.perform(getUserRequest)
                .andExpect(status().isNotFound());

    }

    @Test
    void testGetProject() throws Exception {
        User user = createUser();
        Integer userId = user.getId();

        Project project = new Project();
        String projectName = "My project";
        project.setName(projectName);

        MockHttpServletRequestBuilder createProjectRequest = post("/user/{userId}/project", userId)
                .with(httpBasic("admin", "password"))
                .content(toJson(project))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(createProjectRequest)
                .andExpect(status().isOk());

        String contentAsString = mockMvc.perform(get("/user/{userId}/project", user.getId()).with(httpBasic("admin", "password")))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, Project.class);
        List<Project> projects = objectMapper.readValue(contentAsString, listType);
        Assertions.assertEquals(1, projects.size());
        Assertions.assertEquals(projectName, projects.get(0).getName());

    }

    @Test
    void testWrongAuth() throws Exception {
        User user = createUser();
        Integer userId = user.getId();
        mockMvc.perform(
                        get("/user/{userId}", userId)
                                .with(httpBasic("admin", "wrong")))
                .andExpect(status().isUnauthorized());
    }


    String toJson(Object value) throws Exception {
        return objectMapper.writeValueAsString(value);
    }

}
