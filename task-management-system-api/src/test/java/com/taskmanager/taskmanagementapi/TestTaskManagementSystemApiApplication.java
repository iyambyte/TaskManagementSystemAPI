package com.taskmanager.taskmanagementapi;

import org.springframework.boot.SpringApplication;

public class TestTaskManagementSystemApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(TaskManagementSystemApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
