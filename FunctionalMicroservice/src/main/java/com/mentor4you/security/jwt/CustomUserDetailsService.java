package com.mentor4you.security.jwt;

import com.mentor4you.model.User;
import com.mentor4you.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getAllUsers().stream().filter(
                u -> u.getEmail().equals(username)
        ).findFirst().orElse(null);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(user);
    }



}