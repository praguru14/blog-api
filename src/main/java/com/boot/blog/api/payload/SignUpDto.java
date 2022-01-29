package com.boot.blog.api.payload;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String username;
    private String password;
    private String email;
}
