package org.turnoff.processor.exception;

/**
 * @author liuxin
 * @version Id: FallbackMethodException.java, v 0.1 2019-02-21 11:01
 */
public class FallbackMethodException extends RuntimeException {
  public FallbackMethodException(String message) {
    super(message);
  }

  public FallbackMethodException(String message, Throwable cause) {
    super(message, cause);
  }
}
