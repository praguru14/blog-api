package com.boot.blog.api.services;

import com.boot.blog.api.models.PostModel;
import com.boot.blog.api.payload.PostDto;
import com.boot.blog.api.payload.PostResponse;
import com.boot.blog.api.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServicesImpl implements PostServices {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :Sort.by(sortBy).descending();

        //creating pageable instance
        Pageable page = PageRequest.of(pageNo,pageSize, sort);
        Page<PostModel> lists = postRepository.findAll(page);

        List<PostModel> pageList = lists.getContent();
        List<PostDto> data = pageList.stream().map(list -> mapToDto(list)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(data);
        postResponse.setPageNo(lists.getNumber());
        postResponse.setPageSize(lists.getSize());
        postResponse.setTotalElements(lists.getTotalElements());
        postResponse.setTotalPages(lists.getTotalPages());
        postResponse.setLast(lists.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostsById(int id) {
        PostModel post = postRepository.findById(id).orElse(null);
        return mapToDto(post);
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        //Convert Dto to entity
        //PostModel is our entity
        PostModel postModel = mapToEntity(postDto);
        PostModel newPost = postRepository.save(postModel);

        //Convert Entity to Dto
        //PostDto is our Dto
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostDto updatePostsById(PostDto postDto, int id) {
        PostModel post = postRepository.findById(id).orElse(null);
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        PostModel updated = postRepository.save(post);
        return mapToDto(updated);
    }

    @Override
    public void deletePostsById(int id) {
        PostModel post = postRepository.findById(id).orElse(null);
        postRepository.delete(post);
    }

    private PostDto mapToDto(PostModel postModel){
        PostDto postResponse = new PostDto();
        postResponse.setId(postModel.getId());
        postResponse.setTitle(postModel.getTitle());
        postResponse.setContent(postModel.getContent());
        postResponse.setDescription(postModel.getDescription());
        return postResponse;
    }
    private PostModel mapToEntity(PostDto postDto){
        PostModel postModel = new PostModel();
        postModel.setTitle(postDto.getTitle());
        postModel.setContent(postDto.getContent());
        postModel.setDescription(postDto.getDescription());
        return postModel;
    }
}
