package com.boot.blog.api.repository;


import com.boot.blog.api.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Integer> {

}
