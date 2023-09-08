package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a coordinate for the game
 */
public class Coord {
  private final int xcoord;
  private final int ycoord;

  /**
   * Constructor for Coord class
   * JSON format of this record:
   * <p>
   * <code>
   * {
   * "x": x,
   * "y": y
   * }
   * </code>
   * </p>
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  @JsonCreator
  public Coord(@JsonProperty("x") int x, @JsonProperty("y") int y) {
    if (x >= 15 || x < 0 || y >= 15 || y < 0) {
      throw new IllegalArgumentException("Given values are out of range");
    }

    this.xcoord = x;
    this.ycoord = y;
  }

  /**
   * To get x of the coordinate
   *
   * @return the coordinate location of x
   */
  public int getX() {
    return xcoord;
  }

  /**
   * To get y of the coordinate
   *
   * @return the coordinate location of y
   */
  public int getY() {
    return ycoord;
  }

  /**
   * To return the coordination user-friendly
   *
   * @return a string of the x and y coordinates
   */
  public String toString() {
    return "[" + xcoord + ", " + ycoord + "]";
  }
}
