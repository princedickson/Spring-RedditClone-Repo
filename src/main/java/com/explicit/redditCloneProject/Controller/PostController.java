package com.explicit.redditCloneProject.Controller;


import com.explicit.redditCloneProject.Config.Dto.PostRequestDto;
import com.explicit.redditCloneProject.Config.Dto.PostResponse;
import com.explicit.redditCloneProject.Service.PostService;
import com.explicit.redditCloneProject.Exception.RedditErrorException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@Slf4j
@AllArgsConstructor
public class PostController {


    private PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequestDto postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost() {
        return ResponseEntity.status(HttpStatus.OK)
                .body((postService.getAllPost()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) throws RedditErrorException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPost(id));
    }
    @GetMapping(params = "subredditId")
    public ResponseEntity<PostResponse> getPostBySubreddit( Long subredditId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostBySubreddit(subredditId));
    }

    public ResponseEntity<PostResponse> getPostByUserName( String username){
        return ResponseEntity.status(HttpStatus.OK)
                .body(postService.getPostByUserName(username));
    }
}
