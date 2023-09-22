package com.explicit.redditCloneProject.Repository;

import com.explicit.redditCloneProject.Model.Comment;
import com.explicit.redditCloneProject.Model.Post;
import com.explicit.redditCloneProject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findPostById(Post post);

    List<Comment> findAllByUser(User user);
}
