package com.mentor4you.controller;

import com.mentor4you.domain.PostDTO;
import com.mentor4you.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public Iterable<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }
}
