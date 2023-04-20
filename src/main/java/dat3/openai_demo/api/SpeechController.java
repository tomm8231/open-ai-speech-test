package dat3.openai_demo.api;

import dat3.openai_demo.dtos.SpeechResponse;
import dat3.openai_demo.service.AzureService;
import dat3.openai_demo.service.OpenAiService;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("api/question")
public class SpeechController {

  OpenAiService openAiService;
  AzureService azureService;

  public SpeechController(OpenAiService openAiService, AzureService azureService) {
    this.openAiService = openAiService;
    this.azureService = azureService;
  }


  @GetMapping
  public String getAnswer() throws URISyntaxException, ExecutionException, InterruptedException {

    String questionAsked = azureService.configSpeech();

    SpeechResponse speechResponse = openAiService.getAnAnswer(questionAsked);

    azureService.readOutText(speechResponse.getAnswer());

    return speechResponse.getAnswer();

  }

}
