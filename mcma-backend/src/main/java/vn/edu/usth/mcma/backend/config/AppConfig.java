package vn.edu.usth.mcma.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class AppConfig {
    @Bean
    public static Dotenv dotenv() {
        return Dotenv.configure().load();
    }
}
