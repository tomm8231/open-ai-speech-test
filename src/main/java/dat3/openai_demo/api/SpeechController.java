package dat3.openai_demo.api;


import dat3.openai_demo.dtos.SpeechResponse;
import dat3.openai_demo.service.AzureService;
import dat3.openai_demo.service.OpenAiService;
import dat3.openai_demo.service.OpenAiSpeech;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;


import com.microsoft.cognitiveservices.speech.*;

@RestController
@CrossOrigin
@RequestMapping("api/question")
public class SpeechController {

  OpenAiSpeech openAiSpeech;

  OpenAiService openAiService;
  AzureService azureService;

  public SpeechController(OpenAiService openAiService, AzureService azureService, OpenAiSpeech openAiSpeech) {
    this.openAiService = openAiService;
    this.azureService = azureService;
    this.openAiSpeech = openAiSpeech;
  }

  private static String speechKey = System.getenv("SPEECH_KEY");
  private static String speechRegion = System.getenv("SPEECH_REGION");

  @GetMapping
  public String getAnswer() throws URISyntaxException, ExecutionException, InterruptedException {

    String questionAsked = azureService.configSpeech();

    SpeechResponse speechResponse = openAiService.getAnAnswer(questionAsked);

    azureService.readOutText(speechResponse.answer);

    return speechResponse.getAnswer();

  }

  @PostMapping
  public void handleAudio(@RequestBody byte[] audioData) throws Exception {

    String filePath = "/Users/tommy/Desktop/recording.wav";

    File file = new File(filePath);

    try (FileOutputStream fos = new FileOutputStream(file)) {
      fos.write(audioData);
    } catch (IOException e) {
      throw e;
    }

    openAiSpeech.transcribe(filePath);

  }

}
