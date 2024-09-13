package com.sky.assignment.service;

import com.sky.assignment.model.Project;
import com.sky.assignment.model.User;

import java.util.List;

public interface UserService {
    User getUser(Integer userId);

    User deleteUser(Integer userId);

    User createUser(User user);

    User updateUser(Integer userId, User newUser);

    List<Project> getProjects(Integer userId);

    Project createProject(Integer userId, Project project);
}
