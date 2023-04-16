package dat3.openai_demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyJokeResponse {
  public String joke;
  public String originalPrompt;

  public MyJokeResponse(String joke, String originalPrompt) {
    this.joke = joke;
    this.originalPrompt = originalPrompt;
  }
}
