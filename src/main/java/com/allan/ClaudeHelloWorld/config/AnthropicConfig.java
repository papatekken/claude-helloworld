package com.allan.ClaudeHelloWorld.config;

import com.anthropic.client.AnthropicClient;
import com.anthropic.client.okhttp.AnthropicOkHttpClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;

@Configuration
public class AnthropicConfig {

    @Bean
    public AnthropicClient anthropicClient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("credentials.json");
        try (InputStream is = resource.getInputStream()) {
            JsonNode root = objectMapper.readTree(is);
            String apiKey = root.path("anthropic").path("api-key").asText();
            if (apiKey.isBlank()) {
                throw new IllegalStateException(
                        "Anthropic API key not set — update src/main/resources/credentials.json");
            }
            return AnthropicOkHttpClient.builder()
                    .apiKey(apiKey)
                    .build();
        }
    }
}
