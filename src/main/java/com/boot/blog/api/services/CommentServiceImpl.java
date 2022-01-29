package com.boot.blog.api.services;

import com.boot.blog.api.models.CommentModel;
import com.boot.blog.api.models.PostModel;
import com.boot.blog.api.payload.CommentDto;
import com.boot.blog.api.repository.CommentRepository;
import com.boot.blog.api.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComments(int postId, CommentDto commentDto) {
       CommentModel comment = mapToEntity(commentDto);
       PostModel post = postRepository.findById(postId).orElse(null);
       comment.setPost(post);
      CommentModel newComment =  commentRepository.save(comment);
      return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getAllComments(int postId) {
        List<CommentModel> lists = commentRepository.findAll();
        return lists.stream().map(list -> mapToDto(list)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentsById(int postsId, int commentsId) {
        CommentModel comment = commentRepository.findById(commentsId).orElse(null);
        PostModel post = postRepository.findById(postsId).orElse(null);
        if(!comment.getPost().getId().equals(post.getId())){
            throw new RuntimeException("Comments doesnt belong to post");
        }
        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentsById(int postsId, int commentsId,CommentDto commentDto) {
        CommentModel comment = commentRepository.findById(commentsId).orElse(null);
        PostModel post = postRepository.findById(postsId).orElse(null);
        if(!comment.getPost().getId().equals(post.getId())){
            throw new RuntimeException("Comments doesnt belong to post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

       CommentModel updatedComment= commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteCommentsById(int postsId, int commentsId) {
        CommentModel comment = commentRepository.findById(commentsId).orElse(null);
        PostModel post = postRepository.findById(postsId).orElse(null);
        if(!comment.getPost().getId().equals(post.getId())){
            throw new RuntimeException("Comments doesnt belong to post");
        }
        commentRepository.deleteById(commentsId);
    }


    private CommentDto mapToDto(CommentModel commentModel){
        CommentDto commentDto = modelMapper.map(commentModel,CommentDto.class);
//        commentDto.setBody(commentModel.getBody());
//        commentDto.setEmail(commentModel.getEmail());
//        commentDto.setId(commentModel.getId());
//        commentDto.setName(commentModel.getName());
        return commentDto;
    }
    //Convert Dto to entity
    private CommentModel mapToEntity(CommentDto commentDto){
        CommentModel commentModel = modelMapper.map(commentDto,CommentModel.class);
//        commentModel.setBody(commentDto.getBody());
//        commentModel.setEmail(commentDto.getEmail());
//        commentModel.setName(commentDto.getName());
        return commentModel;
    }

}
