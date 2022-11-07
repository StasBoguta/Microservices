package com.mentor4you.service;

import com.mentor4you.domain.Post;
import com.mentor4you.domain.PostDTO;
import com.mentor4you.domain.User;
import com.mentor4you.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();

    }
}
