package com.explicit.redditCloneProject.Service;

import com.explicit.redditCloneProject.Config.Dto.SubredditDto;
import com.explicit.redditCloneProject.Model.Subreddit;
import com.explicit.redditCloneProject.Repository.SubredditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    @Autowired
    private final SubredditRepository subredditRepository;
    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(mapSuredditDto(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    private Subreddit mapSuredditDto(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getSubredditName())
                .description(subredditDto.getDescription())
                .build();
    }

    @Transactional
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder().SubredditName(subreddit.getName())
                .id(subreddit.getId())
                .numberOfPost(subreddit.getPosts().size())
                .build();
    }
}
