package com.rahenry1.rest.webservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {

	@GetMapping(path = "/hello-world")
	public String sayHelloWorld() {
		return "Hello World";
	}
	
	@GetMapping(path = "/hello-world-international")
	public String sayHelloInternationalWorld() {
		return "Hello World";
	}
}
