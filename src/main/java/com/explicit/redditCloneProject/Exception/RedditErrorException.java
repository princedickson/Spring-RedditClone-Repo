package com.explicit.redditCloneProject.Exception;

public class RedditErrorException extends RuntimeException {
    public RedditErrorException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public RedditErrorException(String exMessage) {
        super(exMessage);
    }
}
