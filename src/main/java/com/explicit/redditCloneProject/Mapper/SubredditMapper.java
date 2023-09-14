package com.explicit.redditCloneProject.Mapper;

import com.explicit.redditCloneProject.Config.Dto.SubredditDto;
import com.explicit.redditCloneProject.Model.Post;
import com.explicit.redditCloneProject.Model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {
    @Mapping(target = "numberOfPost", expression = "java(mapPost(subreddit.getPosts()))")
    @Mapping(source = "name", target = "subredditName")
    SubredditDto mapToDto(Subreddit subreddit);
    default Integer mapPost(List<Post> numberOfPost){
        return numberOfPost.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapToSubredditDto(SubredditDto subredditDto);
}
