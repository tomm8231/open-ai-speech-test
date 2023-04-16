package dat3.openai_demo.api;


import dat3.openai_demo.dtos.MyJokeResponse;
import dat3.openai_demo.service.OpenAiService;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/joke")
public class JokeController {

  OpenAiService service;

  public JokeController(OpenAiService service) {
    this.service = service;
  }

  @GetMapping
  public MyJokeResponse getJoke(@RequestParam String about) throws URISyntaxException {
    return service.getAJoke(about);
  }

}
