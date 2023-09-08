package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "fleet": {}
 * }
 * </code>
 * </p>
 *
 * @param ships the ships formatted for a Json file
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipAdapter> ships) {
}
