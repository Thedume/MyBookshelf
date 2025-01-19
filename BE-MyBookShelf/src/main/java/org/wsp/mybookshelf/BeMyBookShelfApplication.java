package org.wsp.mybookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example.api")
@SpringBootApplication
public class BeMyBookShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeMyBookShelfApplication.class, args);
    }

}
