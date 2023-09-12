package com.explicit.redditCloneProject.Jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthRequest {

    private String username;
    private String password;
}
