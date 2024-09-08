package com.sky.assignment.service;

import com.sky.assignment.model.AdminAccount;
import com.sky.assignment.repository.AdminAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminAccountService implements UserDetailsService {

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AdminAccount> optional = adminAccountRepository.findById(username);
        return optional.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }
}
