package com.mentor4you.service;

import com.mentor4you.domain.Post;
import com.mentor4you.domain.PostDTO;

public interface PostService {

    Iterable<PostDTO> getAllPosts();
}
