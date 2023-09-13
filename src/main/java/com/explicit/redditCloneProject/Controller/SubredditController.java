package com.explicit.redditCloneProject.Controller;

import com.explicit.redditCloneProject.Config.Dto.SubredditDto;
import com.explicit.redditCloneProject.Service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subreddit")
@Slf4j
@AllArgsConstructor
public class SubredditController {

    private SubredditService subredditService;
    @PostMapping
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto){

        if (subredditDto.getDescription() == null) {
            subredditDto.setDescription("");
        }
        if (subredditDto.getNumberOfPost() == null) {
            subredditDto.setNumberOfPost(0);
        }
        return  ResponseEntity.status(HttpStatus.CREATED)
                .body(subredditService.save(subredditDto));
    }
    @GetMapping
    public ResponseEntity<List<SubredditDto>> getAllSubreddit(){
        return ResponseEntity.status(HttpStatus.OK)
                .body((subredditService.getAll()));
    }
}
