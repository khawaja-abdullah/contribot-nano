package io.github.khawajaabdullah;

import java.io.IOException;
import java.util.function.Predicate;

public final class RetryableExecutor {

  public static <T> T executeWithRetry(Task<T> task, Predicate<T> shouldRetry, int maxRetries, long baseDelayMillis, long maxDelayMillis) {
    int attempt = 0;
    while (true) {
      try {
        T result = task.execute();
        if (shouldRetry != null && shouldRetry.test(result)) {
          attempt++;
          if (attempt > maxRetries) {
            return result;
          }
          waitForDelay(attempt, baseDelayMillis, maxDelayMillis);
        } else {
          return result;
        }
      } catch (IOException e) {
        attempt++;
        if (attempt > maxRetries) {
          throw new ContribotNanoException("Max retry attempts (%d) reached due to network/IO failure".formatted(maxRetries), e);
        }
        waitForDelay(attempt, baseDelayMillis, maxDelayMillis);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new ContribotNanoException("Task execution explicitly interrupted", e);
      }
    }
  }

  private static void waitForDelay(int attempt, long baseDelayMillis, long maxDelayMillis) {
    try {
      Thread.sleep(Math.min((long) Math.pow(2, attempt) * baseDelayMillis, maxDelayMillis));
    } catch (InterruptedException ie) {
      Thread.currentThread().interrupt();
      throw new ContribotNanoException("Retry backoff wait period interrupted", ie);
    }
  }

  @FunctionalInterface
  public interface Task<T> {
    T execute() throws IOException, InterruptedException;
  }

}
