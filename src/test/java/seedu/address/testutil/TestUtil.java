package seedu.address.testutil;

import junit.framework.AssertionFailedError;

/**
 * A utility class for test cases.
 */
public class TestUtil {

    public interface AssertThrowsCallback {
        void call() throws Exception;
    }

    public static void assertThrows(Class<? extends Throwable> expected, AssertThrowsCallback callback) {
        try {
            callback.call();
        } catch (Throwable actualException) {
            if (!actualException.getClass().isAssignableFrom(expected)) {
                String message = String.format("Expected thrown: %s, actual: %s", expected.getName(),
                        actualException.getClass().getName());
                throw new AssertionFailedError(message);
            } else {
                return;
            }
        }
        throw new AssertionFailedError(
                String.format("Expected %s to be thrown, but nothing was thrown.", expected.getName()));
    }

}
