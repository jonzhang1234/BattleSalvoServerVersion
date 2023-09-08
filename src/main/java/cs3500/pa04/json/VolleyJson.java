package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "coordinates": {}
 * }
 * </code>
 * </p>
 *
 * @param coords the list of coordinates sent
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<Coord> coords) {
}
