package com.explicit.redditCloneProject.Model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String password;

    @NotEmpty
    @Email
    @Column(name = "Email")
    private String email;

    private boolean isEnable;

    @Column(name = "create date")
    private Instant createDate;

}
