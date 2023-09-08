package cs3500.pa03.model;

import cs3500.pa03.view.GameViewer;
import java.util.List;
import java.util.Random;

/**
 * Responsible for implementing Player for a manual player
 */
public class ManualPlayer extends AbstractPlayer {
  /**
   * Get the player's name.
   *
   * @return Manual for Manual player
   */
  public String name() {
    return "Manual";
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the list of shots took by this player
   */
  public List<Coord> takeShots() {
    GameViewer view = new GameViewer();
    System.out.println("Your Board:");
    view.printBoard(super.board.getLetters(), false);
    int width = super.board.getLetters().length;
    int height = super.board.getLetters()[0].length;
    return view.promptShots(System.in, super.board.getAliveShips(),
        width, height);
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
  }
}
