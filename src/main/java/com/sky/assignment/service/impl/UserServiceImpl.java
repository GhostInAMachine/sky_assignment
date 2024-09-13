package com.sky.assignment.service.impl;

import com.sky.assignment.exception.ResourceNotFoundException;
import com.sky.assignment.model.Project;
import com.sky.assignment.model.User;
import com.sky.assignment.repository.ProjectRepository;
import com.sky.assignment.repository.UserRepository;
import com.sky.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public User getUser(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return userOptional.get();
    }

    @Override
    public User deleteUser(Integer userId) {
        User user = getUser(userId);
        userRepository.deleteById(userId);
        return user;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Integer userId, User newUser) {
        User oldUser = this.getUser(userId);
        patchUser(newUser, oldUser);
        return userRepository.save(oldUser);
    }

    private void patchUser(User newUser, User oldUser) {
        oldUser.setName(newUser.getName());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setEmail(newUser.getEmail());
    }

    @Override
    public List<Project> getProjects(Integer userId) {
        return projectRepository.findProjectsByUserId(userId);
    }

    @Override
    public Project createProject(Integer userId, Project project) {
        User user = getUser(userId);
        project.setUser(user);
        return projectRepository.save(project);
    }
}
