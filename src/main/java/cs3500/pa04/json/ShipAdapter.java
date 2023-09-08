package cs3500.pa04.json;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Direction;
import cs3500.pa03.model.Ship;

/**
 * Adapter class to convert a Ship object to JSON format and vice versa.
 */
public class ShipAdapter {
  private final Coord coord;
  private final int length;
  private final Direction direction;

  /**
   * Constructs a ShipAdapter object from a Ship object.
   *
   * @param ship the Ship object to be adapted
   */
  public ShipAdapter(Ship ship) {
    Coord[] coords = ship.getCoords();
    coord = coords[0];
    if (coords[0].getY() - coords[1].getY() == 0) {
      direction = Direction.HORIZONTAL;
    } else {
      direction = Direction.VERTICAL;
    }
    length = coords.length;
  }

  /**
   * Constructs a ShipAdapter object from JSON properties.
   * JSON format of this record:
   * <p>
   * <code>
   * {
   * "coord": {"x": x, "y": y},
   * "length": length
   * "direction": "VERTICAL" or "HORIZONTAL"
   * }
   * </code>
   * </p>
   *
   * @param c   the first coordinate
   * @param len the length of the ship
   * @param dir the direction of the ship
   */
  @JsonCreator
  public ShipAdapter(@JsonProperty("coord") Coord c, @JsonProperty("length") int len,
                     @JsonProperty("direction") Direction dir) {
    coord = c;
    length = len;
    direction = dir;
  }

  /**
   * Returns the coordinate of the ship.
   *
   * @return the coordinate of the ship
   */
  public Coord getCoord() {
    return coord;
  }

  /**
   * Returns the direction of the ship.
   *
   * @return the direction of the ship
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * Returns the length of the ship.
   *
   * @return the length of the ship
   */
  public int getLength() {
    return length;
  }
}
