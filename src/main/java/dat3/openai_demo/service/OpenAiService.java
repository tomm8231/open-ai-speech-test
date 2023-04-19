package dat3.openai_demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat3.openai_demo.dtos.SpeechResponse;
import dat3.openai_demo.dtos.OpenApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAiService{

  @Value("${app.api-key}")
  private String API_KEY;

  String URL = "https://api.openai.com/v1/completions";

  String FIXED_PROMPT = "Please answer the question: ";

  WebClient client = WebClient.create();

  public SpeechResponse getAnAnswer(String prompt) throws URISyntaxException {

    String inputPrompt = FIXED_PROMPT + prompt;

    Map<String, Object> body = new HashMap<>();

    body.put("model","text-davinci-003");
    body.put("prompt",inputPrompt);
    body.put("temperature", 1);
    body.put("max_tokens", 50);
    body.put("top_p", 1);
    body.put("frequency_penalty", 0.2);
    body.put("presence_penalty", 0);

    ObjectMapper mapper = new ObjectMapper();
    String json = "";
    try {
      json = mapper.writeValueAsString(body);
    } catch (Exception e) {
      e.printStackTrace();
    }

    OpenApiResponse response = client.post()
            .uri(new URI(URL))
            .header("Authorization", "Bearer " +API_KEY)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(json))
            .retrieve()
            .bodyToMono(OpenApiResponse.class)
            .block();
    String answer = response.choices.get(0).text;
    return new SpeechResponse(answer,inputPrompt);
  }




}
