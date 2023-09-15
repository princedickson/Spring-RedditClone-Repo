package com.explicit.redditCloneProject.Repository;

import com.explicit.redditCloneProject.Model.Post;
import com.explicit.redditCloneProject.Model.Subreddit;
import com.explicit.redditCloneProject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
