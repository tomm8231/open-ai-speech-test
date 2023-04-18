package dat3.openai_demo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpeechResponse {
  public String answer;
  public String originalPrompt;

  public SpeechResponse(String answer, String originalPrompt) {
    this.answer = answer;
    this.originalPrompt = originalPrompt;
  }
}
