package dev.sudu.userservicesept28;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UserServiceSept28Application {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceSept28Application.class, args);
    }

}
