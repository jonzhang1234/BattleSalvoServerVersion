package cs3500.pa04.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.ArtificalPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.MockPlayer;
import cs3500.pa04.Mocket;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupRequestJson;
import cs3500.pa04.json.VolleyJson;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private ObjectMapper mapper = new ObjectMapper();
  private Map<ShipType, Integer> specification;


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());

    specification = new HashMap<>();
    specification.put(ShipType.CARRIER, 2);
    specification.put(ShipType.BATTLESHIP, 1);
    specification.put(ShipType.DESTROYER, 1);
    specification.put(ShipType.SUBMARINE, 1);
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testHandleJoin() {
    JsonNode sampleMessage = createSampleMessage("join", mapper.createObjectNode());

    Mocket socket = new Mocket(this.testLog, List.of(sampleMessage.toString()));

    try {
      this.controller = new ProxyController(socket, new ArtificalPlayer());
    } catch (IOException e) {
      fail();
    }

    this.controller.run();

    StringBuilder expected = new StringBuilder(
        "{\"method-name\":\"join\",\"arguments\":{\"name\":\"jonzhang1234\",\"game-type\":"
            + "\"SINGLE\"}}");
    assertEquals(expected.toString().trim(), logToString().trim());
  }

  /**
   * Check that the server returns a guess when given a hint.
   */
  @Test
  public void testHandle() {
    SetupRequestJson request = new SetupRequestJson(10, 10, specification);

    JsonNode sampleSetup = createSampleMessage("setup", JsonUtils.serializeRecord(request));
    JsonNode sampleTakeShot = createSampleMessage("take-shots", mapper.createObjectNode());
    JsonNode sampleReportDamage = createSampleMessage("report-damage", mapper.createObjectNode());
    JsonNode sampleSuccessfulHits =
        createSampleMessage("successful-hits", mapper.createObjectNode());
    JsonNode sampleEndGame = createSampleMessage("end-game", mapper.createObjectNode());


    Mocket socket = new Mocket(this.testLog,
        List.of(sampleSetup.toString(), sampleTakeShot.toString(), sampleReportDamage.toString(),
            sampleSuccessfulHits.toString(), sampleEndGame.toString()));

    try {
      this.controller = new ProxyController(socket, new MockPlayer());
    } catch (IOException e) {
      fail();
    }

    this.controller.run();

    StringBuilder expected = new StringBuilder(
        "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":{\"x\":0,\"y\":0},\""
            + "length\":2,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":0},\"length"
            + "\":2,\"direction\":\"HORIZONTAL\"},{\"coord\":{\"x\":0,\"y\":0},\"length\":2,"
            + "\"direction\":\"VERTICAL\"},{\"coord\":{\"x\":0,\"y\":0},\"length\":2,\""
            + "direction\":\"VERTICAL\"}]}}\n" + "{\"method-name\":\"take-shots\",\""
            + "arguments\":{\"coordinates\":[{\"x\":0,\"y\":0},{\"x\":0,\"y\":0}]}}\n"
            + "{\"method-name\":\"report-damage\",\"arguments\":{\"coordinates\":[]}}\n"
            + "{\"method-name\":\"successful-hits\",\"arguments\":{}}\n"
            + "{\"method-name\":\"end-game\",\"arguments\":{}}");

    assertEquals(expected.toString().trim(), logToString().trim());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Create a MessageJson for some name and arguments.
   *
   * @param messageName name of the type of message; "hint" or "win"
   * @param node        object to embed in a message json
   * @return a MessageJson for the object
   */
  private JsonNode createSampleMessage(String messageName, JsonNode node) {
    MessageJson messageJson = new MessageJson(messageName, node);
    return JsonUtils.serializeRecord(messageJson);
  }
}