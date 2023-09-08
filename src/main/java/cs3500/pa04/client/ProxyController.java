package cs3500.pa04.client;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameType;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa04.json.EndGameJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupRequestJson;
import cs3500.pa04.json.ShipAdapter;
import cs3500.pa04.json.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class ProxyController {

  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player player;
  private final ObjectMapper mapper = new ObjectMapper();

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.player = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      System.err.println("disconnected");
    }
  }

  /**
   * Handles the "join" message from the server.
   *
   * @return the response as a JSON node
   */
  private JsonNode handleJoin() {
    JoinJson response = new JoinJson("jonzhang1234", GameType.SINGLE);

    return JsonUtils.serializeRecord(response);
  }

  /**
   * Handles the "setup" message from the server.
   *
   * @param arguments the arguments of the setup message
   * @return the response as a JSON node
   */
  private JsonNode handleSetup(JsonNode arguments) {
    SetupRequestJson request = this.mapper.convertValue(arguments, SetupRequestJson.class);
    List<Ship> ships = this.player.setup(request.height(), request.width(), request.specs());
    List<ShipAdapter> shipAdapters = new ArrayList<>();
    for (Ship s : ships) {
      shipAdapters.add(new ShipAdapter(s));
    }
    FleetJson response = new FleetJson(shipAdapters);
    return JsonUtils.serializeRecord(response);
  }

  /**
   * Handles the "take-shots" message from the server.
   *
   * @return the response as a JSON node
   */
  private JsonNode handleTakeShots() {
    List<Coord> coords = player.takeShots();
    VolleyJson response = new VolleyJson(coords);

    return JsonUtils.serializeRecord(response);
  }

  /**
   * Handles the "report-damage" message from the server.
   *
   * @param arguments the arguments of the report-damage message
   * @return the response as a JSON node
   */
  private JsonNode handleReportDamage(JsonNode arguments) {
    VolleyJson request = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> opHits = player.reportDamage(request.coords());
    VolleyJson response = new VolleyJson(opHits);

    return JsonUtils.serializeRecord(response);
  }

  /**
   * Handles the "successful-hits" message from the server.
   *
   * @param arguments the arguments of the successful-hits message
   * @return the response as a JSON node
   */
  private JsonNode handleSuccessfulHits(JsonNode arguments) {
    VolleyJson request = this.mapper.convertValue(arguments, VolleyJson.class);
    player.successfulHits(request.coords());

    return mapper.createObjectNode();
  }

  /**
   * Handles the "end-game" message from the server.
   *
   * @param arguments the arguments of the end-game message
   * @return the response as a JSON node
   */
  private JsonNode handleEndgame(JsonNode arguments) {
    EndGameJson request = this.mapper.convertValue(arguments, EndGameJson.class);
    player.endGame(request.result(), request.reason());

    return mapper.createObjectNode();
  }

  /**
   * Wraps the message with the given name and JSON response and sends it to the server.
   *
   * @param name         the name of the message
   * @param jsonResponse the JSON response to be sent
   * @throws JsonProcessingException if there is an error serializing the JSON message
   */
  private void wrapMessage(String name, JsonNode jsonResponse) throws JsonProcessingException {
    MessageJson message = new MessageJson(name, jsonResponse);
    this.out.println(mapper.writeValueAsString(message));
  }


  /**
   * Determines the type of request the server has sent ("join", "setup", "take-shots",
   * "report-damage", "successful-hits", or "end-game") and delegates to the corresponding helper
   * method with the message arguments.
   *
   * @param message the MessageJSON used to determine what the server has sent
   * @throws JsonProcessingException if there is an error serializing the JSON response
   */
  private void delegateMessage(MessageJson message) throws JsonProcessingException {
    String name = message.messageName();
    JsonNode arguments = message.arguments();
    JsonNode response;
    if ("join".equals(name)) {
      response = this.handleJoin();
    } else if ("setup".equals(name)) {
      response = this.handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      response = this.handleTakeShots();
    } else if ("report-damage".equals(name)) {
      response = this.handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      response = this.handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      response = this.handleEndgame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }

    wrapMessage(name, response);
  }
}