package com.allan.ClaudeHelloWorld.controller;

import com.allan.ClaudeHelloWorld.service.ClaudeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    private final ClaudeService claudeService;

    public HelloController(ClaudeService claudeService) {
        this.claudeService = claudeService;
    }

    /**
     * GET /
     * Sends a fixed "Hello World" greeting prompt to Claude and returns its reply.
     */
    @GetMapping("/")
    public String hello() {
        return claudeService.ask("Say 'Hello World' in a creative and friendly way.");
    }

    /**
     * GET /ask?message=...
     * Sends any user-supplied message to Claude and returns its reply.
     */
    @GetMapping("/ask")
    public String ask(@RequestParam(defaultValue = "Who are you?") String message) {
        return claudeService.ask(message);
    }

    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}

