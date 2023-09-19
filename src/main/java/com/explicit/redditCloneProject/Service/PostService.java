package com.explicit.redditCloneProject.Service;

import com.explicit.redditCloneProject.Config.Dto.PostRequestDto;
import com.explicit.redditCloneProject.Config.Dto.PostResponse;
import com.explicit.redditCloneProject.Exception.PostNotFoundException;
import com.explicit.redditCloneProject.Exception.SubredditNotFoundException;
import com.explicit.redditCloneProject.Mapper.PostMapper;
import com.explicit.redditCloneProject.Model.Post;
import com.explicit.redditCloneProject.Model.Subreddit;
import com.explicit.redditCloneProject.Model.User;
import com.explicit.redditCloneProject.Repository.PostRepository;
import com.explicit.redditCloneProject.Repository.SubredditRepository;
import com.explicit.redditCloneProject.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    @Autowired
    private final SubredditRepository subredditRepository;
    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final PostMapper postMapper;
    @Autowired
    private final UserRepository userRepository;

    public void save(PostRequestDto postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit not found with name: " + postRequest.getSubredditName()));
        postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
        //Post post = postMapper.map(postRequest, subreddit, authService.getCurrentUser());
        /*Post savedPost = postRepository.save(post);

        return postMapper.mapToDto(savedPost);*/
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPost() {
        return
                postRepository.findAll()
                        .stream()
                        .map(postMapper::mapToDto)
                        .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);

    }

    @Transactional(readOnly = true)
    public PostResponse getPostBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return (PostResponse) posts.
                stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPostByUserName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return (PostResponse) postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}