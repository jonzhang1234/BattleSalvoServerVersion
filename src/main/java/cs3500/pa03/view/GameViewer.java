package cs3500.pa03.view;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.SalvoInfo;
import cs3500.pa03.model.ShipType;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Responsible for viewing a battleship game
 */
public class GameViewer {

  /**
   * Prompts user for shots
   *
   * @param stream the stream to take inputs
   * @param alive  the number of alive ships the player has
   * @param width  the width of the player's board
   * @param height the height of the player's board
   * @return the list of Coords to shot at
   */
  public List<Coord> promptShots(InputStream stream, int alive, int width, int height) {
    Scanner in = new Scanner(stream);
    List<Coord> coords = new ArrayList<>();

    System.out.println("Please Enter " + alive + " Shots (x, y):");

    int x;
    int y;
    for (int s = 0; s < alive; s += 1) {
      x = in.nextInt();
      y = in.nextInt();
      if (x >= 0 && x < width && y >= 0 && y < height) {
        coords.add(new Coord(x, y));
      } else {
        System.out.println("Invalid coordinates, x and y must be within (" + width
            + ", " + height + "), try again:");
        s -= 1;
      }
    }
    return coords;
  }

  /**
   * Prints the given board
   *
   * @param letters the letters of the board to print
   * @param hidden  whether the ships should be hidden
   */
  public void printBoard(char[][] letters, boolean hidden) {
    int cols = letters.length;
    int rows = letters[0].length;

    for (int r = 0; r < rows; r += 1) {
      for (int c = 0; c < cols; c += 1) {
        if (hidden) {
          if (letters[c][r] == 'H' || letters[c][r] == 'M') {
            System.out.print(letters[c][r] + "  ");
          } else {
            System.out.print("-  ");
          }
        } else {
          System.out.print(letters[c][r] + "  ");
        }
      }
      System.out.println();
    }
  }
}
