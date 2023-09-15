package com.explicit.redditCloneProject.Service;

import com.explicit.redditCloneProject.Config.Dto.SubredditDto;
import com.explicit.redditCloneProject.Exception.RedditErrorException;
import com.explicit.redditCloneProject.Mapper.SubredditMapper;
import com.explicit.redditCloneProject.Model.Subreddit;
import com.explicit.redditCloneProject.Repository.SubredditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService {

    @Autowired
    private final SubredditRepository subredditRepository;
    @Autowired
    private final SubredditMapper subredditMapper;
    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(subredditMapper.mapToSubredditDto(subredditDto));
        subredditDto.setId(save.getId());
        return subredditDto;
    }

    @Transactional
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapToDto)
                .collect(toList());
    }

    public SubredditDto getSubreddit(Long id) throws RedditErrorException {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(()-> new RedditErrorException("No user found with id -" + id));
        return subredditMapper.mapToDto(subreddit);
    }
}
