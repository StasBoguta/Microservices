package com.mentor4you.service;

import com.mentor4you.config.WatchedPostsMetrics;
import com.mentor4you.domain.Category;
import com.mentor4you.domain.Post;
import com.mentor4you.domain.PostDTO;
import com.mentor4you.domain.User;
import com.mentor4you.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final WatchedPostsMetrics watchedPostsMetrics;

    private final PostRepository postRepository;
    private final AuthService authService;

    @Override
    public Iterable<PostDTO> getAllPosts() {
        final List<PostDTO> result = new ArrayList<>();
        postRepository.findAll().forEach(post -> result.add(toPostDTO(post)));
        watchedPostsMetrics.watchedPostsCounter.increment(1.0);
        String token = authService.login();
        log.info("Logged in through Protobuf API, received token: {}", token);
        return result;
    }

    private PostDTO toPostDTO(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(User.builder().id(post.getAuthorId()).email(post.getAuthorEmail()).build())
                .category(Category.builder().id(post.getCategoryId()).name(post.getCategoryName()).build())
                .build();
    }
}
