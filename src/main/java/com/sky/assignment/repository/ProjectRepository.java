package com.sky.assignment.repository;

import com.sky.assignment.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findProjectsByUserId(Integer userId);

}
