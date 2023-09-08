package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for board class
 */
public class BoardTest {
  private AbstractPlayer player1;
  private AbstractPlayer player2;
  private Map<ShipType, Integer> specs;
  private Board board;
  private List<Ship> ships;

  /**
   * Set up before testing
   */
  @BeforeEach
  public void setup() {
    player1 = new ManualPlayer();
    player2 = new ArtificalPlayer();
    specs = new HashMap<>();

    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 2);
    specs.put(ShipType.DESTROYER, 2);
    specs.put(ShipType.SUBMARINE, 1);
    ships = player1.setup(10, 10, specs);
    board = new Board(ships, 10, 10);
  }

  /**
   * Test for the hit shots
   */
  @Test
  public void testSetHits() {
    List<Coord> hits = new ArrayList<>();
    Collections.addAll(hits, ships.get(0).getCoords());

    assertEquals(6, board.getAliveShips());
    board.setHits(hits);
    char[][] letters = board.getLetters();
    for (Coord c : hits) {
      assertEquals('H', letters[c.getX()][c.getY()]);
    }
    assertEquals(5, board.getAliveShips());
  }

  /**
   * Test for miss shots
   */
  @Test
  public void testSetMiss() {
    List<Coord> misses = new ArrayList<>();
    misses.add(new Coord(0, 0));
    misses.add(new Coord(0, 1));
    misses.add(new Coord(1, 0));
    misses.add(new Coord(1, 1));

    board.setMisses(misses);
    char[][] letters = board.getLetters();
    for (Coord c : misses) {
      assertEquals('M', letters[c.getX()][c.getY()]);
    }
  }
}
