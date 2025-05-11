package com.site.slowprint.chatbot.service;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatbotService {

    @Value("${gemini.api.key}")
    private String API_KEY;

    public String getAnswer(String message) throws Exception {
        String responseText;
        // Gemini 모델 초기화
        try (VertexAI vertexAI = new VertexAI("gen-lang-client-0685167275", "asia-northeast3")) {
            GenerativeModel model = new GenerativeModel("gemini-1.5-pro", vertexAI);

            // 콘텐츠 생성 요청
            GenerateContentResponse response = model.generateContent(
                    ContentMaker.fromString("다음은 지속가능한 여행에 관련한 질문이야 : " + message
                            + "지속가능한 여행을 위해 질문에 적절한 답변을 질문의 언어와 동일한 언어로 제공해줘.")
            );

            responseText = ResponseHandler.getText(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return responseText;
    }
}
