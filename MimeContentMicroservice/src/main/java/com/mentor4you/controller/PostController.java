package com.mentor4you.controller;

import com.mentor4you.converter.ContentConverter;
import com.mentor4you.domain.Category;
import com.mentor4you.domain.Post;
import com.mentor4you.domain.PostDTO;
import com.mentor4you.domain.User;
import com.mentor4you.service.CategoryService;
import com.mentor4you.service.PostService;
import com.mentor4you.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private static final String TEXT_CSV_VALUE = "text/csv";

    private final PostService postService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ContentConverter<PostDTO> csvPostConverter;
    private final ContentConverter<PostDTO> htmlPostConverter;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<PostDTO> getAllPostsJson() {
        return getPostDTOs();
    }

    @GetMapping(produces = TEXT_CSV_VALUE)
    public ResponseEntity<String> getAllPostsCsv() {
        final Iterable<PostDTO> posts = getPostDTOs();
        final String postsAsCsv = csvPostConverter.convert(posts);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, TEXT_CSV_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"posts.csv\"")
                .body(postsAsCsv);
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<?> getAllPostsHtml() {
        final Iterable<PostDTO> posts = getPostDTOs();
        final String postsAsHtml = htmlPostConverter.convert(posts);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .body(postsAsHtml);
    }

    private Iterable<PostDTO> getPostDTOs() {
        final Iterable<Post> posts = postService.getAllPosts();
        final List<PostDTO> postDTOs = new ArrayList<>();
        posts.forEach(post -> {
            final PostDTO postDTO = toPostDTO(post);
            postDTOs.add(postDTO);
        });
        return postDTOs;
    }

    private PostDTO toPostDTO(Post post) {
        try {
            CompletableFuture<User> author = userService.getUserById(post.getAuthorId());
            CompletableFuture<Category> category = categoryService.getCategoryById(post.getCategoryId());
            CompletableFuture.allOf(author, category).join();

            return PostDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(author.get())
                    .category(category.get())
                    .build();
        } catch (ExecutionException | InterruptedException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }
}
