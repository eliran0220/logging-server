package com.example.LogSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableWebMvc
public class LogSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogSystemApplication.class, args);
	}


}
