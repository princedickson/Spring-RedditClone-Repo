package com.explicit.redditCloneProject.Repository;


import com.explicit.redditCloneProject.Model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
