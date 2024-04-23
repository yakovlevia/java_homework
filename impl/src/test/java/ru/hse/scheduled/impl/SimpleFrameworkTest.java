package ru.hse.scheduled.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import ru.hse.scheduled.api.annotation.Scheduled;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class SimpleFrameworkTest {

    private final SimpleFramework simpleFramework = new SimpleFramework();

    @AfterEach
    public void tearDown() {
        simpleFramework.close();
    }


    public static class Demo {
        /**
         * Ideally this should be non-static field.
         * But for simplicity it is a static field
         */
        private static final CountDownLatch invoked = new CountDownLatch(1);

        @Scheduled
        public void demo() {
            invoked.countDown();
        }

        public static void awaitUntilInvoked() throws InterruptedException {
            invoked.await();
        }
    }

    @Test
    @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void invokesTheMethod() throws InterruptedException {
        try {
            simpleFramework.start(Demo.class);
            Demo.awaitUntilInvoked();
        } finally {
            simpleFramework.close();
        }
    }
}