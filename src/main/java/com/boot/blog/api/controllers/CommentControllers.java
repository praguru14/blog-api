package com.boot.blog.api.controllers;

import com.boot.blog.api.payload.CommentDto;
import com.boot.blog.api.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentControllers {
    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(value = "postId") int postId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComments(postId, commentDto), HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComments(@PathVariable(value = "postId") int postId){
        return new ResponseEntity<>(commentService.getAllComments(postId), HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<CommentDto> getCommentsById(@PathVariable(value = "postId") int postId,@PathVariable(value="commentsId") int commentsId){
        return new ResponseEntity<>(commentService.getCommentsById(postId,commentsId), HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<CommentDto> updateCommentsById(@PathVariable(value = "postId") int postId,@PathVariable(value="commentsId") int commentsId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentsById(postId,commentsId, commentDto), HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentsId}")
    public ResponseEntity<?> deleteCommentsById(@PathVariable(value = "postId") int postId,@PathVariable(value="commentsId") int commentsId,@RequestBody CommentDto commentDto){
        commentService.deleteCommentsById(postId,commentsId);
        return new ResponseEntity<>( "Deleted successfully",HttpStatus.OK);
    }
}
