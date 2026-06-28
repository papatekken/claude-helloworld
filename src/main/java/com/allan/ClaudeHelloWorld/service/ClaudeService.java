package com.allan.ClaudeHelloWorld.service;

import com.anthropic.client.AnthropicClient;
import com.anthropic.models.messages.Message;
import com.anthropic.models.messages.MessageCreateParams;
import com.anthropic.models.messages.Model;
import com.anthropic.models.messages.ContentBlock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClaudeService {

    private final AnthropicClient client;
    private final String model;
    private final long maxTokens;

    public ClaudeService(
            AnthropicClient client,
            @Value("${anthropic.model}") String model,
            @Value("${anthropic.max-tokens}") long maxTokens) {
        this.client = client;
        this.model = model;
        this.maxTokens = maxTokens;
    }

    public String ask(String userMessage) {
        MessageCreateParams params = MessageCreateParams.builder()
                .model(Model.of(model))
                .maxTokens(maxTokens)
                .addUserMessage(userMessage)
                .build();

        Message message = client.messages().create(params);

        return message.content().stream()
                .filter(ContentBlock::isText)
                .map(block -> block.asText().text())
                .findFirst()
                .orElse("No response from Claude.");
    }
}
