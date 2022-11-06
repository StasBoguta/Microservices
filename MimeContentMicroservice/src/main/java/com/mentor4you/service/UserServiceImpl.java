package com.mentor4you.service;

import com.mentor4you.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public User getUserById(Integer id) {
        // TODO: request to other service
        return User.builder().build();
    }
}
