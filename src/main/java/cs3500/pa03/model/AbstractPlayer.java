package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Responsible for abstracting the behaviors of Manual and AI player
 */
public abstract class AbstractPlayer implements Player {
  protected Board board;
  protected boolean[][] alreadyShot;

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  public abstract String name();

  /**
   * Given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the player's list of ships
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    ArrayList<Ship> ships = new ArrayList<>();

    for (ShipType st : specifications.keySet()) {
      int num = specifications.get(st);
      int size = st.size;
      for (int i = 0; i < num; i += 1) {
        Coord[] coords = generateCoords(new Random(), height, width, size);
        while (checkOverlaps(ships, coords)) {
          coords = generateCoords(new Random(), height, width, size);
        }
        ships.add(new Ship(st, coords));
      }
    }

    board = new Board(ships, width, height);
    alreadyShot = new boolean[width][height];
    return ships;
  }

  /**
   * Helper method to randomly generate ship positions
   *
   * @return coordinates representing placement of a ship
   */
  private Coord[] generateCoords(Random rand, int height, int width, int size) {
    int y;
    int x;
    Coord[] coords = new Coord[size];

    if (rand.nextDouble(1) > 0.5) { // vertical
      y = rand.nextInt(height - size + 1);
      x = rand.nextInt(width);
      for (int i = 0; i < size; i += 1) {
        coords[i] = new Coord(x, y + i);
      }
    } else { // horizontal
      y = rand.nextInt(height);
      x = rand.nextInt(width - size + 1);
      for (int i = 0; i < size; i += 1) {
        coords[i] = new Coord(x + i, y);
      }
    }

    return coords;
  }

  /**
   * Helper method to check for overlaps
   *
   * @param ships  the ships to check
   * @param coords the coords to check
   * @return true if there is an overlap and false otherwise
   */
  private boolean checkOverlaps(ArrayList<Ship> ships, Coord[] coords) {
    for (Ship s : ships) {
      for (Coord c1 : s.getCoords()) {
        for (Coord c2 : coords) {
          if (c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public abstract List<Coord> takeShots();

  /**
   * Given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    List<Coord> hits = new ArrayList<>();
    List<Coord> shipHits = new ArrayList<>();
    List<Coord> misses = new ArrayList<>();

    for (Coord c1 : board.getShipCoords().keySet()) {
      for (Coord c2 : opponentShotsOnBoard) {
        if (c1.getX() == c2.getX() && c1.getY() == c2.getY()) {
          shipHits.add(c1);
          hits.add(c2);
        } else {
          misses.add(c2);
        }
      }
    }
    board.setHits(shipHits);
    board.setMisses(misses);
    return hits;
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public abstract void successfulHits(List<Coord> shotsThatHitOpponentShips);


  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public void endGame(GameResult result, String reason) {
    return;
  }
}
