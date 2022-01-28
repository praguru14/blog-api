package com.boot.blog.api.services;

import com.boot.blog.api.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostServices {

    List<PostDto> getPosts();
    PostDto createPost(PostDto postDto);
}
