package com.explicit.redditCloneProject.Service;

public class RedditErrorException extends Throwable {
    public RedditErrorException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
