package cs3500.pa04;

import cs3500.pa03.controller.GameController;
import cs3500.pa03.model.ArtificalPlayer;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.view.ViewDisplay;
import cs3500.pa04.client.ProxyController;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      Random rand = new Random();
      Player player = new ManualPlayer();
      Player opponent = new ArtificalPlayer();
      ViewDisplay view = new ViewDisplay(System.out);

      GameController controller = new GameController(player, opponent, view);

      Scanner in = new Scanner(System.in);
      controller.run(in);
    } else if (args.length == 2) {
      String host = args[0];
      int port = Integer.parseInt(args[1]);

      Socket server = new Socket(host, port);
      ProxyController proxyController = new ProxyController(server, new ArtificalPlayer());
      proxyController.run();
      server.close();
    } else {
      throw new IllegalArgumentException(
          "Please enter no command-line arguments to run human vs. CPU or please enter host and "
              + "port to run local CPU vs. server CPU");
    }
  }
}