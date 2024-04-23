package ru.hse.scheduled.impl;

/**
 * A wrapper for a {@link Runnable} which catches all exceptions and logs them
 */
public class PrintExceptionRunnable implements Runnable {
    /**
     * The target {@link Runnable}
     */
    private final Runnable runnable;

    /**
     * A constructor with a target {@link Runnable}
     *
     * @param runnable the target
     */
    public PrintExceptionRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Same as usual {@link Runnable#run()}: invokes the target's {@link Runnable#run()} method but logs all exceptions
     * thrown from it
     */
    @Override
    public void run() {
        try {
            runnable.run();
        } catch (Throwable throwable) {
            System.err.println(throwable.getMessage());
            throwable.printStackTrace(System.err);
        }
    }
}
