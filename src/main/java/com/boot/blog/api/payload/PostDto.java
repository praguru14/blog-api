package com.boot.blog.api.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int id;
    @NotEmpty
    @Size(min = 2,message = "Title should have atleast 2 char")
    private String title;
    @NotEmpty
    @Size(min = 10,message = "Description should have atleast 10 char")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDto> comments = new HashSet<>();
}
