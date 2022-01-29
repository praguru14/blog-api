package com.boot.blog.api.controllers;

import com.boot.blog.api.payload.PostDto;
import com.boot.blog.api.payload.PostResponse;
import com.boot.blog.api.services.PostServices;
import com.boot.blog.api.utils.PostConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostControllers {
    @Autowired
    private PostServices postServices;

    @GetMapping("/")
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(value="pageNo",defaultValue = PostConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNo,
            @RequestParam(value="pageSize",defaultValue = PostConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = PostConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue = PostConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir
    ){
    return new ResponseEntity<>(postServices.getPosts(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostsById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(postServices.getPostsById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
    try{
        return new ResponseEntity<>(postServices.createPost(postDto), HttpStatus.CREATED);
    }
    catch (Exception ex){
        ex.printStackTrace();
        return new ResponseEntity<>(postServices.createPost(postDto), HttpStatus.NOT_FOUND);
    }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePostsById(@Valid @RequestBody PostDto postDto,@PathVariable(name = "id") int id){
        return new ResponseEntity<>(postServices.updatePostsById(postDto,id),HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostsById(@PathVariable(name = "id") int id){
        postServices.deletePostsById(id);
        return new ResponseEntity<>("Post entity deleted successfully",HttpStatus.OK);
    }

}
