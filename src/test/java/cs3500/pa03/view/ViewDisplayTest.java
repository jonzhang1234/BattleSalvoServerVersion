package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Test for view display class
 */
class ViewDisplayTest {
  /**
   * Test for add method that display the given string line
   */
  @Test
  public void testAdd() {
    StringBuilder output = new StringBuilder();
    ViewDisplay viewDisplay = new ViewDisplay(output);

    String line = "Hello! Welcome to the OOD BattleSalvo Game!";
    viewDisplay.add(line);

    assertEquals(line + System.lineSeparator(), output.toString());
  }

}