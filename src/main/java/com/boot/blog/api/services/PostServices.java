package com.boot.blog.api.services;

import com.boot.blog.api.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostServices {

    List<PostDto> getPosts();
    PostDto getPostsById(int id);
    PostDto createPost(PostDto postDto);
    PostDto updatePostsById(PostDto postDto,int id);
    void deletePostsById(int id);
}
