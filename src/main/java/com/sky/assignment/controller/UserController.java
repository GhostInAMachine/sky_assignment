package com.sky.assignment.controller;

import com.sky.assignment.model.Project;
import com.sky.assignment.model.User;
import com.sky.assignment.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return userService.getUser(userId);
    }

    @DeleteMapping("/{userId}")
    public User deleteUser(@PathVariable("userId") Integer userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable("userId") Integer userId, @Valid @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    @PostMapping("/{userId}/project")
    public Project createProject(@PathVariable("userId") Integer userId, @RequestBody @Valid Project project) {
        return userService.createProject(userId, project);
    }

    @GetMapping("/{userId}/project")
    public List<Project> getProjects(@PathVariable("userId") Integer userId) {
        return userService.getProjects(userId);
    }
}
