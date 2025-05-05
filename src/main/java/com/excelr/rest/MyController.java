package com.excelr.rest;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
@RestController
@RequestMapping("/api")
public class MyController {
	private static final String CIRCUIT_BREAKER_NAME = "myCircuitBreaker";
	private final AtomicInteger counter = new AtomicInteger(0);
	@GetMapping("/test")
	@CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "fallbackMethod")
	public String firstMethod() {
		int currentCount = counter.incrementAndGet();
		System.out.println("Request count: " + currentCount);
		if (currentCount <= 5) {
			return "Executing first method! Call number: " + currentCount;
		} else {
			// Forcefully trigger a failure
			throw new RuntimeException("Simulated Exception new data added for githu check new check 2");
		}
	}
	public String fallbackMethod(Throwable e) {
		return secondMethod();
	}
	public String secondMethod() {
		return "Executing second method!";
	}
}