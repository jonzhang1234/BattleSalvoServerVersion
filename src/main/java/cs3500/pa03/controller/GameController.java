package cs3500.pa03.controller;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.SalvoInfo;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.ViewDisplay;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Responsible for controlling the game
 */
public class GameController {
  private final Player player;
  private final Player opponent;
  private final ViewDisplay display;

  /**
   * Constructor for GameController
   *
   * @param player   the player
   * @param opponent the opponent
   * @param display  the viewer
   */
  public GameController(Player player, Player opponent, ViewDisplay display) {
    this.player = player;
    this.opponent = opponent;
    this.display = display;
  }

  /**
   * Runs the game
   *
   * @param in the Scanner to read the input
   */
  public void run(Scanner in) {
    int[] dim = adjustDimension(in);
    Map<ShipType, Integer> specs = promptShips(in, Math.min(dim[0], dim[1]));
    player.setup(dim[0], dim[1], specs);
    opponent.setup(dim[0], dim[1], specs);

    List<Coord> opponentShots = opponent.takeShots();
    List<Coord> playerShots = player.takeShots();

    while (playerShots.size() > 0 && opponentShots.size() > 0) {
      List<Coord> opponentHits = player.reportDamage(opponentShots);
      List<Coord> playerHits = opponent.reportDamage(playerShots);
      player.successfulHits(playerHits);
      opponent.successfulHits(opponentHits);
      List<Coord> playerMissed =
          playerShots.stream().filter((c) -> !playerHits.contains(c)).toList();
      List<Coord> opponentMissed =
          opponentShots.stream().filter((c) -> !opponentHits.contains(c)).toList();

      SalvoInfo info = new SalvoInfo(playerHits, playerMissed, opponentHits, opponentMissed);
      printShots(info);
      opponentShots = opponent.takeShots();
      playerShots = player.takeShots();
    }

    if (opponentShots.size() == 0 && playerShots.size() == 0) {
      player.endGame(GameResult.DRAW, "All your and opponents ships sank");
      opponent.endGame(GameResult.DRAW, "All opponents and your ships sank");
    } else if (playerShots.size() == 0) {
      player.endGame(GameResult.LOSE, "All your ships sank");
      opponent.endGame(GameResult.WIN, "All opponents ships sank");
    } else {
      opponent.endGame(GameResult.LOSE, "All your ships sank");
      player.endGame(GameResult.WIN, "All opponents ships sank");
    }

  }

  /**
   * Prints the shots fired information.
   *
   * @param info the SalvoInfo object containing the shots information
   */
  private void printShots(SalvoInfo info) {
    display.add("Shots fired by the user which hit AI ships:\n" + info.getUserHits());
    display.add("Shots fired by the user which did not hit ships:\n" + info.getUserMissed());
    display.add("Shots fired by the AI which hit user ships:\n" + info.getOpponentHits());
    display.add("Shots fired by the AI which did not hit user ships:\n" + info.getOpponentMissed());
  }

  /**
   * Prompts the user to input their fleet sizes and returns the fleet in hash map.
   *
   * @param in          the Scanner to read the input
   * @param smallestDim the smaller dimension of the game board
   * @return a map of ShipType to Integer representing the fleet specifications
   */
  private Map<ShipType, Integer> promptShips(Scanner in, int smallestDim) {
    display.add(
        "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    display.add("Remember, your fleet may not exceed size " + smallestDim + ".");

    int c = in.nextInt();
    int b = in.nextInt();
    int d = in.nextInt();
    int s = in.nextInt();

    while (c < 1 || b < 1 || d < 1 || s < 1 || c + b + d + s > smallestDim) {
      display.add("Uh Oh! You've entered invalid fleet sizes.");
      display.add(
          "Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
      display.add("Remember, your fleet may not exceed size " + smallestDim + ".");
      c = in.nextInt();
      b = in.nextInt();
      d = in.nextInt();
      s = in.nextInt();
    }
    Map<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, c);
    specs.put(ShipType.BATTLESHIP, b);
    specs.put(ShipType.DESTROYER, d);
    specs.put(ShipType.SUBMARINE, s);
    return specs;
  }

  /**
   * Adjust the dimension of the game play board
   *
   * @param in the scanner to read the input
   * @return return the array of height and width of the board
   */
  private int[] adjustDimension(Scanner in) {
    display.add("Hello! Welcome to the OOD BattleSalvo Game!");
    display.add("Please enter a valid height and width below:");
    int height = in.nextInt();
    int width = in.nextInt();
    while (height < 6 || height > 15 || width < 6 || width > 15) {
      display.add("Uh Oh! You've entered invalid dimensions.");
      display.add("Please remember that the height and width");
      display.add("of the game must be in the range (6, 10), inclusive. Try again!");
      height = in.nextInt();
      width = in.nextInt();
    }

    return new int[] {height, width};
  }
}
