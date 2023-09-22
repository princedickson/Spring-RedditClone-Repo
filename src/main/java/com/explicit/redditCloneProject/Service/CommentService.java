package com.explicit.redditCloneProject.Service;

import com.explicit.redditCloneProject.Config.Dto.CommentDto;
import com.explicit.redditCloneProject.Exception.PostNotFoundException;

import com.explicit.redditCloneProject.Mapper.CommentMapper;
import com.explicit.redditCloneProject.Model.Comment;
import com.explicit.redditCloneProject.Model.NotificationEmail;
import com.explicit.redditCloneProject.Model.Post;
import com.explicit.redditCloneProject.Model.User;
import com.explicit.redditCloneProject.Repository.CommentRepository;
import com.explicit.redditCloneProject.Repository.PostRepository;
import com.explicit.redditCloneProject.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final AuthService authService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final CommentMapper commentMapper;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final MailContentBuilder mailContentBuilder;
    @Autowired
    private final MailService mailService;

    public void save(CommentDto commentDto) {

        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentDto> getAllCommentByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findPostById(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username.toString()));
         return commentRepository.findAllByUser(user)
                 .stream()
                 .map(commentMapper::mapToDto)
                 .collect(Collectors.toList());
    }
}
