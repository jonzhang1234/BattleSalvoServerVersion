package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.Socket;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test the driver class
 */
class DriverTest {
  private String[] args;

  /**
   * Set up before the test
   */
  @BeforeEach
  public void setup() {
    args = new String[] {};
  }

  /**
   * Test when no arguments are given
   */
  @Test
  public void testGame() {
    assertThrows(NoSuchElementException.class, () -> Driver.main(args));
  }


  /**
   * Test when two arguments are given
   */
  @Test
  public void testServer() {
    args = new String[] {"0.0.0.0", "35001"};
    assertThrows(java.net.ConnectException.class, () -> Driver.main(args));
  }

  /**
   * Test for invalid argument
   */
  @Test
  public void testInvalid() {
    args = new String[] {"35001"};
    assertThrows(java.lang.IllegalArgumentException.class, () -> Driver.main(args));
  }
}