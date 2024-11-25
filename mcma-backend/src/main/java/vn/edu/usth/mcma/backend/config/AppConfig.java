package vn.edu.usth.mcma.backend.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public static Dotenv dotenv() {
        return Dotenv.configure().load();
    }
}
