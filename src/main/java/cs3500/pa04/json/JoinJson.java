package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameType;

/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "name": "github_username",
 * "game-type": "SINGLE" or "MULTI"
 * }
 * </code>
 * </p>
 *
 * @param name the name of the user
 * @param type the game type of the game
 */
public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") GameType type) {
}
