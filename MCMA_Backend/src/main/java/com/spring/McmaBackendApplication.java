package com.spring;

import com.spring.entities.User;
import com.spring.enums.Type;
import com.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class McmaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(McmaBackendApplication.class, args);
    }
}
