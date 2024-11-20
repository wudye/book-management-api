package com.mypractice.book;

import com.mypractice.book.role.Role;
import com.mypractice.book.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class BookNetworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookNetworkApiApplication.class, args);

	}

	@Bean
	public CommandLineRunner runner(RoleRepository rolerepository) {
		return args -> {
			if (rolerepository.findByName("USEr").isEmpty()) {
				rolerepository.save(
						Role.builder().name("USER").build()
				);
			}
		};
	}

}
