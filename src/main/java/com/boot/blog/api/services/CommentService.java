package com.boot.blog.api.services;

import com.boot.blog.api.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService{
    CommentDto createComments(int postId,CommentDto commentDto);
    List<CommentDto> getAllComments(int postId);
    CommentDto getCommentsById(int postsId, int commentsId);
    CommentDto updateCommentsById(int postsId,int commentsId,CommentDto commentDto);
    void deleteCommentsById(int postsId,int commentsId);
}
