package dat3.openai_demo.service;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class AzureService {

  private static String speechKey = System.getenv("SPEECH_KEY");
  private static String speechRegion = System.getenv("SPEECH_REGION");


  public String configSpeech() throws ExecutionException, InterruptedException {
    SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);
    speechConfig.setSpeechRecognitionLanguage("en-US");

    String questionAsked = recognizeFromMicrophone(speechConfig);

    return questionAsked;

  }
  public static String recognizeFromMicrophone(SpeechConfig speechConfig) throws InterruptedException, ExecutionException {
    AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();
    SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig);

    System.out.println("Speak into your microphone.");
    Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
    SpeechRecognitionResult speechRecognitionResult = task.get();

    if (speechRecognitionResult.getReason() == ResultReason.RecognizedSpeech) {
      System.out.println("RECOGNIZED: Text=" + speechRecognitionResult.getText());
      return speechRecognitionResult.getText();
    }
    else if (speechRecognitionResult.getReason() == ResultReason.NoMatch) {
      System.out.println("NOMATCH: Speech could not be recognized.");
    }
    else if (speechRecognitionResult.getReason() == ResultReason.Canceled) {
      CancellationDetails cancellation = CancellationDetails.fromResult(speechRecognitionResult);
      System.out.println("CANCELED: Reason=" + cancellation.getReason());

      if (cancellation.getReason() == CancellationReason.Error) {
        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
        System.out.println("CANCELED: Did you set the speech resource key and region values?");
      }
    }

    //System.exit(0);
    return null;
  }

  public void readOutText(String response) throws InterruptedException, ExecutionException {
    SpeechConfig speechConfig = SpeechConfig.fromSubscription(speechKey, speechRegion);

    speechConfig.setSpeechSynthesisVoiceName("en-US-JennyNeural");

    SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

    /*// Get text from the console and synthesize to the default speaker.
    System.out.println("Enter some text that you want to speak >");
    String text = new Scanner(System.in).nextLine();
    if (text.isEmpty())
    {
      return;
    }

     */

    SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(response).get();

    if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
      System.out.println("Speech synthesized to speaker for text [" + response + "]");
    }
    else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
      SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails.fromResult(speechSynthesisResult);
      System.out.println("CANCELED: Reason=" + cancellation.getReason());

      if (cancellation.getReason() == CancellationReason.Error) {
        System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
        System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
        System.out.println("CANCELED: Did you set the speech resource key and region values?");
      }
    }

  }
}
