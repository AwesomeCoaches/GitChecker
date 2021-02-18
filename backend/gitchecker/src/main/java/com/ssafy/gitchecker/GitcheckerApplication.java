package com.ssafy.gitchecker;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class GitcheckerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(GitcheckerApplication.class)
			.listeners(new ApplicationPidFileWriter())
			.run(args);
	}

}
