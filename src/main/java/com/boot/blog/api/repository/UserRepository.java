package com.boot.blog.api.repository;

import com.boot.blog.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel,Integer> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByUsernameOrEmail(String username,String email);
    Optional<UserModel> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
