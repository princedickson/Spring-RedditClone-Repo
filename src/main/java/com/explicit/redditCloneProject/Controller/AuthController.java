package com.explicit.redditCloneProject.Controller;

import com.explicit.redditCloneProject.Config.Dto.RegisterRequest;
import com.explicit.redditCloneProject.Jwt.*;
import com.explicit.redditCloneProject.Service.AuthService;
import com.explicit.redditCloneProject.Exception.RedditErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestBody RegisterRequest registerRequest) throws RedditErrorException {
        authService.signUpUser(registerRequest);
        return new ResponseEntity<>("user registration successfully ", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) throws RedditErrorException {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activated", HttpStatus.OK);
    }

    @PostMapping("/authenticated")
    public JwtResponse getJwtToken(@RequestBody JwtAuthRequest jwtAuthRequest) throws RedditErrorException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            RefreshToken refreshToken =refreshTokenService.createRefreshToken(jwtAuthRequest.getUsername());
            return JwtResponse.builder()
                    .accessToken(jwtService.generateToken(jwtAuthRequest.getUsername()))
                    .token(refreshToken.getToken()).build();
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
}
