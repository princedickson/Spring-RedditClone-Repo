package com.explicit.redditCloneProject.Mapper;

import com.explicit.redditCloneProject.Config.Dto.PostRequestDto;
import com.explicit.redditCloneProject.Config.Dto.PostResponse;
import com.explicit.redditCloneProject.Model.Post;
import com.explicit.redditCloneProject.Model.Subreddit;
import com.explicit.redditCloneProject.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "Spring")
public interface PostMapper {


    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    Post map(PostRequestDto postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);
}
