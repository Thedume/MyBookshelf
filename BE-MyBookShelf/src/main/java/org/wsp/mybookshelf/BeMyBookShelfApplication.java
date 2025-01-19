package org.wsp.mybookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example.api")
=======
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
>>>>>>> ef4d9322cabfa11199b9fee8d65e5f14f3839b59
@SpringBootApplication
public class BeMyBookShelfApplication {

    public static void main(String[] args) {
        SpringApplication.run(BeMyBookShelfApplication.class, args);
    }

}
