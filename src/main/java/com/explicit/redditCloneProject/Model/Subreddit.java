package com.explicit.redditCloneProject.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "name can not be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "entre a description")
    @Column(name = "description")
    private String description;

    @Column(name = "create date")
    private Instant createDate;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}
