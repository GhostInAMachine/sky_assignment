package com.sky.assignment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sky.assignment.repository.AttributeEncryptor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_account") // "user" collides with Postgres reserved keywords
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Email
    @NotNull
    private String email;

    @Convert(converter = AttributeEncryptor.class)
    @NotNull
    private String password;

    @OneToMany(targetEntity = Project.class, mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projects = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }
}
