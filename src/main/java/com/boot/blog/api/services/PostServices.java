package com.boot.blog.api.services;

import com.boot.blog.api.payload.PostDto;
import com.boot.blog.api.payload.PostResponse;
import org.springframework.stereotype.Service;

@Service
public interface PostServices {

    PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostsById(int id);
    PostDto createPost(PostDto postDto);
    PostDto updatePostsById(PostDto postDto,int id);
    void deletePostsById(int id);
}
