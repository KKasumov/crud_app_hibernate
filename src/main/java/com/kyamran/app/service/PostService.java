package com.kyamran.app.service;

import com.kyamran.app.model.Post;
import com.kyamran.app.repository.PostRepository;
import com.kyamran.app.repository.hibernateImpl.PostRepositoryImpl;

import java.util.List;

public class PostService {
    PostRepository postRepository = PostRepositoryImpl.getInstance();

    public Post save(Post post) { return postRepository.save(post); }

    public Post update(Post post) {
        return postRepository.update(post);
    }

    public Post getById(Long Long) {
        return postRepository.getById(Long);
    }

    public void deleteById(Long Long) {
        postRepository.deleteById(Long);
    }

    public List<Post> getAll() {
        return postRepository.getAll();
    }

}