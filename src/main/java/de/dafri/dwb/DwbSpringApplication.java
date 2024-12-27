package de.dafri.dwb;

import de.dafri.dwb.data.CategoryDto;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DwbSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DwbSpringApplication.class, args);
    }

    @Bean
    ApplicationRunner init(CategoryDto dto) {
        return args -> dto.init();
    }
}
