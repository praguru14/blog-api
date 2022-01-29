package com.boot.blog.api.repository;

import com.boot.blog.api.models.RoleModel;
import com.boot.blog.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleModel,Integer> {
    Optional<RoleModel> findByName(String name);
}
