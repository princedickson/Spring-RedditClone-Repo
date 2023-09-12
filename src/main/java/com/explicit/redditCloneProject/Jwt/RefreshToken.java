package com.explicit.redditCloneProject.Jwt;

import com.explicit.redditCloneProject.Model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.security.SecurityProperties;


import java.sql.ConnectionBuilder;
import java.time.Instant;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Instant createdDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

}
