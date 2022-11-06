package org.example.service;

import org.example.domain.Post;
import org.example.domain.PostDTO;
import org.example.domain.User;
import org.example.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    public Iterable<PostDTO> getAllPosts() {
        return StreamSupport
                .stream(postRepository.findAll().spliterator(), false)
                .map(this::toPostDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Integer id) {
        return postRepository
                .findById(id)
                .map(this::toPostDTO)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Post with id=%s does not exist", id)));
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
