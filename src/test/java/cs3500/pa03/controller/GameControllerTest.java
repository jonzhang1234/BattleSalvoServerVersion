package cs3500.pa03.controller;

import cs3500.pa03.controller.GameController;
import cs3500.pa03.model.ArtificalPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.ViewDisplay;
import cs3500.pa04.MockPlayer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for game controller
 */
public class GameControllerTest {
  Player player;
  Player opponent;
  ViewDisplay view;
  GameController controller;

  /**
   * set up before testing
   */
  @BeforeEach
  public void setup() {
    player = new MockPlayer();
    opponent = new ArtificalPlayer();
    view = new ViewDisplay(System.out);

    controller = new GameController(player, opponent, view);
  }


  /**
   * Test for running the game
   *
   * @throws IOException if the file is not read
   */
  @Test
  public void testRun() throws IOException {
    Path sampleFile = Path.of("src/test/resources/controllerInput.txt");
    InputStream sampleInput = Files.newInputStream(sampleFile);
    Scanner in = new Scanner(sampleInput);
    controller.run(in);
  }
}