package org.example.prepurchase.domain.post.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.prepurchase.domain.post.application.PostService;
import org.example.prepurchase.domain.post.dto.CreatePostDto;
import org.example.prepurchase.domain.post.dto.LovePostDto;
import org.example.prepurchase.global.error.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(HttpServletRequest request, @Valid @RequestBody CreatePostDto createPost) {
        String username = request.getHeader("username");

        postService.createPost(username, createPost);

        return ResponseEntity.ok().body("포스트가 생성되었습니다.");
    }


    @PostMapping("/love")
    public ResponseEntity<String> lovePost(HttpServletRequest request) {

        try {
            postService.lovePost(request);
            return ResponseEntity.ok().body("게시물을 좋아합니다.");
        } catch(IllegalArgumentException e) {
            ErrorDto errorDto = new ErrorDto(e.getMessage());
            return ResponseEntity.ok().body(errorDto.getMessage());
        }
    }
}
