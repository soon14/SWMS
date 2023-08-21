package com.swms.search;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.huggingface.HuggingFaceChatModel;
import dev.langchain4j.model.huggingface.HuggingFaceEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("ai")
@RestController
public class AiController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping(value = "/chat")
    public Object chat(String message) {
        HuggingFaceChatModel huggingFaceChatModel = HuggingFaceChatModel.withAccessToken("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");
        AiMessage hell = huggingFaceChatModel.sendUserMessage(message);

        HuggingFaceEmbeddingModel huggingFaceEmbeddingModel = HuggingFaceEmbeddingModel.withAccessToken("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");
        Embedding embed = huggingFaceEmbeddingModel.embed(message);
        return embed.toString();
    }
}
