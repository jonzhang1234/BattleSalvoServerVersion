package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.SalvoInfo;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Test for salvo info class
 */
public class SalvoInfoTest {

  /**
   * Test for the getter methods
   */
  @Test
  public void testGetters() {
    List<Coord> userHits = new ArrayList<>();
    userHits.add(new Coord(0, 0));

    List<Coord> userMisses = new ArrayList<>();
    userMisses.add(new Coord(0, 1));

    List<Coord> opponentHits = new ArrayList<>();
    opponentHits.add(new Coord(1, 0));

    List<Coord> opponenetMisses = new ArrayList<>();
    opponenetMisses.add(new Coord(1, 1));

    SalvoInfo info = new SalvoInfo(userHits, userMisses, opponentHits, opponenetMisses);

    assertEquals("[0, 0], ", info.getUserHits());
    assertEquals("[0, 1], ", info.getUserMissed());
    assertEquals("[1, 0], ", info.getOpponentHits());
    assertEquals("[1, 1], ", info.getOpponentMissed());
  }

}
