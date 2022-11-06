package org.example.service;

import org.example.domain.Post;
import org.example.domain.PostDTO;

public interface PostService {

    Iterable<PostDTO> getAllPosts();

    PostDTO getPostById(Integer id);
}
