package com.mentor4you.controller;

import com.mentor4you.converter.ContentConverter;
import com.mentor4you.domain.Post;
import com.mentor4you.domain.PostDTO;
import com.mentor4you.service.PostService;
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
import java.util.List;

@Controller
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private static final String TEXT_CSV_VALUE = "text/csv";

    private final PostService postService;
    private final ContentConverter<PostDTO> csvPostConverter;
    private final ContentConverter<PostDTO> htmlPostConverter;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Iterable<PostDTO> getAllPostsJson() {
        return postService.getAllPosts();
    }

    @GetMapping(produces = TEXT_CSV_VALUE)
    public ResponseEntity<String> getAllPostsCsv() {
        final Iterable<PostDTO> posts = postService.getAllPosts();
        final String postsAsCsv = csvPostConverter.convert(posts);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, TEXT_CSV_VALUE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"posts.csv\"")
                .body(postsAsCsv);
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<?> getAllPostsHtml() {
        final Iterable<PostDTO> posts =
        final String postsAsHtml = htmlPostConverter.convert(posts);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE)
                .body(postsAsHtml);
    }

    private Iterable<PostDTO> getPostsFromDTO() {
        final Iterable<PostDTO> posts = postService.getAllPosts();
    }

    private PostDTO toPostDTO(Post post) {
        try {
            User author = userService.getUserById(post.getAuthorId());
            return PostDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .author(author)
                    .build();
        } catch(IllegalArgumentException ex) {
            throw new IllegalArgumentException(
                    String.format("Error retrieving post with id=%s: user with id=%s does not exist",
                            post.getId(),
                            post.getAuthorId()));
        }
    }
}
