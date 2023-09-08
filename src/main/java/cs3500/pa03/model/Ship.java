package cs3500.pa03.model;

/**
 * Responsible for representing a BattleSalvo ship
 */
public class Ship {
  private final ShipType type;
  private final Coord[] coords;

  /**
   * Represent the ship class
   *
   * @param t represent the ship type
   * @param c represent the coord locations
   */
  public Ship(ShipType t, Coord[] c) {
    type = t;
    coords = c;
  }

  /**
   * to determine the type of the ship
   *
   * @return the ship type
   */
  public ShipType getType() {
    return type;
  }

  /**
   * to determine the coordinate location
   *
   * @return the coordinate of the ship
   */
  public Coord[] getCoords() {
    return coords;
  }
}
