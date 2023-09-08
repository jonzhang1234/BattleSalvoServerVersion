package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.Map;


/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "width": width,
 * "height": height
 * "fleet-spec": {
 * "CARRIER": number,
 * "BATTLESHIP": number,
 * "DESTROYER": number,
 * "SUBMARINE": number
 * }
 * }
 * </code>
 * </p>
 *
 * @param width  the width of the board
 * @param height the height of the board
 * @param specs  the specifications of the number of types of ships
 */
public record SetupRequestJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> specs) {
}
