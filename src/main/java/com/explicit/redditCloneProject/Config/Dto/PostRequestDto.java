package com.explicit.redditCloneProject.Config.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private String postId;
    private String subredditName;
    @NotBlank(message = "post name can not be null")
    private String postName;
    private String url;
    private String description;
}
