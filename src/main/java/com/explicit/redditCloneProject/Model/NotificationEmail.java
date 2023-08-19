package com.explicit.redditCloneProject.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEmail {
    private String Subject;
    private String recipient;
    private String body;
}
