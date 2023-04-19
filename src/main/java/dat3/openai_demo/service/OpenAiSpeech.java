package dat3.openai_demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;


@Service
public class OpenAiSpeech {

  private static final String API_URL = "https://api.openai.com/v1/speech-to-text";

  @Value("${app.api-key}")
  private String apiKey;

  private RestTemplate restTemplate;


  public OpenAiSpeech() {
    this.restTemplate = new RestTemplate();
    this.restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
  }



  public String transcribe(String audioUrl) throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "Bearer " + apiKey);

    JSONObject data = new JSONObject();
    data.put("audio_url", audioUrl);
    HttpEntity<String> requestEntity = new HttpEntity<>(data.toString(), headers);

    ResponseEntity<String> responseEntity = restTemplate.exchange(API_URL, HttpMethod.POST, requestEntity, String.class);
    String responseString = responseEntity.getBody();

    JSONObject jsonResponse = new JSONObject(responseString);
    System.out.println(jsonResponse.getString("text"));
    return jsonResponse.getString("text");
  }

}
