package com.explicit.redditCloneProject.Service;

import com.explicit.redditCloneProject.Config.Dto.RegisterRequest;
import com.explicit.redditCloneProject.Exception.RedditErrorException;
import com.explicit.redditCloneProject.Model.NotificationEmail;
import com.explicit.redditCloneProject.Model.User;
import com.explicit.redditCloneProject.Model.VerificationToken;
import com.explicit.redditCloneProject.Repository.UserRepository;
import com.explicit.redditCloneProject.Repository.VerificationRepository;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Component
public class AuthService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final VerificationRepository verificationRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    UserRepository userRepository;
    public AuthService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
                       VerificationRepository verificationRepository, MailService mailService,
                       AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.verificationRepository = verificationRepository;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public void signUpUser(RegisterRequest registerRequest) throws RedditErrorException {

        if (registerRequest.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        User user = new User();

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreateDate(Instant.now());
        user.setEnable(false);
        userRepository.save(user);


        String token = (generateVerificationToken(user));
        mailService.sendMail(new NotificationEmail("Please Activate your Account",
                user.getEmail(), "Thank you for signing up to Spring Reddit, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));

    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) throws RedditErrorException {
        Optional<VerificationToken> verificationToken = verificationRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new RedditErrorException("invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) throws RedditErrorException {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RedditErrorException("user name not fond -" + username));

        if (username.isEmpty()) {
            throw new RedditErrorException("user already exit");
        }
        user.setEnable(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        } else {
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
        }
    }
}