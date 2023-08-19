package com.explicit.redditCloneProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditCloneProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditCloneProjectApplication.class, args);
	}

}
