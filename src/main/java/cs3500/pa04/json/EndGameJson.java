package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;


/**
 * JSON format of this record:
 * <p>
 * <code>
 * {
 * "result": "WIN", "DRAW" or "LOSE",
 * "reason": "reason"
 * }
 * </code>
 * </p>
 *
 * @param result the result of the game
 * @param reason the reason the game ended
 */
public record EndGameJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason) {
}
