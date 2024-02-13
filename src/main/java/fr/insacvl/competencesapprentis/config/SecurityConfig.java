package fr.insacvl.competencesapprentis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    // This method defines a bean for BCryptPasswordEncoder
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // Instantiates and returns an instance of BCryptPasswordEncoder
        return new BCryptPasswordEncoder();
    }
}