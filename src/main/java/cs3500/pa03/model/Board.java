package cs3500.pa03.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Responsible for representing a battleship board
 */
public class Board {
  private final Map<Coord, Ship> shipCoords;
  private int aliveShips;
  private final char[][] letters;

  /**
   * Constructor for Board class
   *
   * @param shps   list of ships to be placed on the board
   * @param width  width of board
   * @param height height of board
   */
  public Board(List<Ship> shps, int width, int height) {
    shipCoords = new HashMap<>();
    aliveShips = shps.size();
    letters = new char[width][height];
    for (int x = 0; x < width; x += 1) {
      for (int y = 0; y < height; y += 1) {
        letters[x][y] = '-';
      }
    }

    for (Ship s : shps) {
      for (Coord c : s.getCoords()) {
        shipCoords.put(c, s);
        letters[c.getX()][c.getY()] = s.getType().toString().charAt(0);
      }
    }
  }

  /**
   * Get the alive ships
   *
   * @return the amount of a lived ships
   */
  public int getAliveShips() {
    return aliveShips;
  }

  /**
   * Get the coordinate of the ship
   *
   * @return the hashmap of the coordinates of individual ships
   */
  public Map<Coord, Ship> getShipCoords() {
    return shipCoords;
  }

  /**
   * Get the letters
   *
   * @return 2d array of letter characters
   */
  public char[][] getLetters() {
    return letters;
  }

  /**
   * sets characters of board on the given coords to 'H'
   * and removes the given coords from the shipCoords map
   * and modifies aliveShips if ships were sunk
   *
   * @param hits list of hit coordinates
   */
  public void setHits(List<Coord> hits) {
    for (Coord c : hits) {
      letters[c.getX()][c.getY()] = 'H';
      shipCoords.remove(c);
    }
    aliveShips = new HashSet<>(shipCoords.values()).size();
  }

  /**
   * sets characters of board on the given coords to 'M' unless they are already 'H'
   *
   * @param misses list of missed coordinates
   */
  public void setMisses(List<Coord> misses) {
    for (Coord c : misses) {
      if (letters[c.getX()][c.getY()] != 'H') {
        letters[c.getX()][c.getY()] = 'M';
      }
    }
  }
}
