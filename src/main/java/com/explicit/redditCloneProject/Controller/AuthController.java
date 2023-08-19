package com.explicit.redditCloneProject.Controller;

import com.explicit.redditCloneProject.Config.Dto.RegisterRequest;
import com.explicit.redditCloneProject.Service.AuthService;
import com.explicit.redditCloneProject.Service.RedditErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
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
}
