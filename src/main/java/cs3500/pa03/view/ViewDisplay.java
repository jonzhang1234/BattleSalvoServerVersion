package cs3500.pa03.view;

import java.io.IOException;
import java.util.Objects;

/**
 * Represent the view class to display various messages to the user
 */
public class ViewDisplay {
  private Appendable output;

  public ViewDisplay(Appendable output) {
    this.output = Objects.requireNonNull(output);
  }

  /**
   * Represent the line to display
   *
   * @param line The line or the instruction that the user can see
   */
  public void add(String line) {
    try {
      output.append(line);
      output.append(System.lineSeparator());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
