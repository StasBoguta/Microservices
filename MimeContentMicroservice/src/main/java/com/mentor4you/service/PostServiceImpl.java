package com.mentor4you.service;

import com.mentor4you.config.WatchedPostsMetrics;
import com.mentor4you.domain.Category;
import com.mentor4you.domain.Post;
import com.mentor4you.domain.PostDTO;
import com.mentor4you.domain.User;
import com.mentor4you.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    private final WatchedPostsMetrics watchedPostsMetrics;

    private final PostRepository postRepository;

    @Override
    public Iterable<PostDTO> getAllPosts() {
        final List<PostDTO> result = new ArrayList<>();
        postRepository.findAll().forEach(post -> result.add(toPostDTO(post)));
        watchedPostsMetrics.watchedPostsCounter.increment(1.0);
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
