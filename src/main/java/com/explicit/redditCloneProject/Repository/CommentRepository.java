package com.explicit.redditCloneProject.Repository;

import com.explicit.redditCloneProject.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
