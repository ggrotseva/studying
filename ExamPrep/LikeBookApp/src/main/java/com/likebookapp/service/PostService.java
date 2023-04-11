package com.likebookapp.service;

import com.likebookapp.model.dtos.PostAddDTO;
import com.likebookapp.model.dtos.PostViewDTO;
import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.model.enums.MoodName;
import com.likebookapp.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final MoodService moodService;
    private final AuthService authService;

    @Autowired
    public PostService(PostRepository postRepository, MoodService moodService, AuthService authService) {
        this.postRepository = postRepository;
        this.moodService = moodService;
        this.authService = authService;
    }

    public void addPost(PostAddDTO postAddDTO) {
        User currentUser = this.authService.getLoggedUser();
        Mood mood = this.moodService.getMoodByName(postAddDTO.getMood());

        Post newPost = new Post()
                .setContent(postAddDTO.getContent())
                .setMood(mood)
                .setAuthor(currentUser);

        this.postRepository.saveAndFlush(newPost);
    }

    @Transactional
    public void likePost(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow();

        User loggedUser = this.authService.getLoggedUser();

        if (post.getLikes().contains(loggedUser)) {
            post.removeLike(loggedUser);
        } else {
            post.addLike(loggedUser);
        }
    }

    public void deletePost(Long id) {
        this.postRepository.deleteById(id);
    }

    @Transactional
    public List<PostViewDTO> getPostsByCurrentUser() {
        return this.postRepository.findByAuthorId(authService.getLoggedUser().getId())
                .stream().map(post -> new PostViewDTO().mapToView(post))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<PostViewDTO> getPostsByCurrentUserNot() {
        return this.postRepository.findByAuthorIdNot(authService.getLoggedUser().getId())
                .stream().map(post -> new PostViewDTO().mapToView(post))
                .collect(Collectors.toList());
    }

}
