package br.com.brunodorea.sdw2024.adapters.out;

import br.com.brunodorea.sdw2024.domain.ports.GenerativeAiService;
import feign.FeignException;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "GEMINI", matchIfMissing = true)
@FeignClient(name = "geminiApi", url = "${gemini.base-url}", configuration = GoogleGeminiService.Config.class)
public interface GoogleGeminiService extends GenerativeAiService {

    @PostMapping("/v1beta/models/gemini-pro:generateContent")
    GoogleGeminiResp textOnlyInput(GoogleGeminiReq req);

    @Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s
                """.formatted(objective, context);
        GoogleGeminiReq req = new GoogleGeminiReq(
                List.of(new Content(List.of(new Part(prompt))))
        );
        try {
            GoogleGeminiResp resp = textOnlyInput(req);
            return resp.candidates().getFirst().contents().parts().getFirst().text();
        } catch(FeignException httpErrors) {
            return "Deu ruim! Erro de comunicação com a API do Google Gemini.";
        } catch (Exception unexpectedError) {
            return "Foi mal! O retorno da API do Google Gemini não contem os dados esperados.";
        }
    }

    record GoogleGeminiReq(List<Content> contents){}
    record Content(List<Part> parts) {}
    record Part(String text) {}

    record GoogleGeminiResp(List<Candidate> candidates){}
    record Candidate(Content contents) {}

    class Config {
        public RequestInterceptor apikeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.query("key", apiKey);
        }
    }
}
