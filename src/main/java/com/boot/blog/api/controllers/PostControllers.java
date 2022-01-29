package com.boot.blog.api.controllers;

import com.boot.blog.api.payload.PostDto;
import com.boot.blog.api.services.PostServices;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostControllers {
    @Autowired
    private PostServices postServices;

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getPosts(){
    return new ResponseEntity<>(postServices.getPosts(),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(postServices.getPostsById(id),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
    try{
        return new ResponseEntity<>(postServices.createPost(postDto), HttpStatus.CREATED);
    }
    catch (Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(postServices.createPost(postDto), HttpStatus.NOT_FOUND);
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostsById(@RequestBody PostDto postDto,@PathVariable(name = "id") int id){
        return new ResponseEntity<>(postServices.updatePostsById(postDto,id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostsById(@PathVariable(name = "id") int id){
        postServices.deletePostsById(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);
    }

}
