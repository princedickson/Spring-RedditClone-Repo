package com.explicit.redditCloneProject.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public String myKey(){
        return "Your-Secret-Key";

    }
}
