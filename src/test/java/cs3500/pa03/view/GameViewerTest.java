package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.view.GameViewer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for game viwer
 */
public class GameViewerTest {
  GameViewer view;

  /**
   * Basic setup before testing
   */
  @BeforeEach
  public void setup() {
    view = new GameViewer();
  }

  /**
   * Test the prompt shot
   *
   * @throws IOException if error when reading file
   */
  @Test
  public void testPromptShots() throws IOException {
    Path sampleFile = Path.of("src/test/resources/shots.txt");
    InputStream sampleInput = Files.newInputStream(sampleFile);

    List<Coord> shots = view.promptShots(sampleInput, 6, 10, 10);
    int i = 0;
    for (Coord c : shots) {
      assertEquals(0, c.getX());
      assertEquals(i, c.getY());
      i += 1;
    }
  }

  /**
   * Test for printing out the board
   */
  @Test
  public void testPrintBoard() {
    char[][] sampleLetters = {{'S', 'H'}, {'M', '0'}};
    view.printBoard(sampleLetters, false);
    view.printBoard(sampleLetters, true);
  }
}
