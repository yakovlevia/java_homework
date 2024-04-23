package ru.hse.scheduled.client;

import ru.hse.scheduled.api.Feature;
import ru.hse.scheduled.api.Framework;
import ru.hse.scheduled.api.annotation.Scheduled;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ServiceLoader;

/**
 * The demo application
 */
public class Application {
    /**
     * Demo {@link Scheduled} method.
     * Runs each 1s
     */
    @Scheduled(times = 10)
    private void sayHello() {
        System.out.println("Hello! Time is " + LocalDateTime.now());
    }

    /**
     * The application entry point
     *
     * @param args the application arguments
     */
    public static void main(String[] args) {
        try (Framework framework = getFramework()) {
            System.out.println(framework);

            if (!framework.supports(Feature.TIMES)) {
                System.out.println("Framework do not support times");
                return;
            }

            framework.start(Application.class);
            try {
                Thread.sleep(Duration.ofSeconds(15));
            } catch (InterruptedException e) {
                System.out.println("Interrupted during sleep. Message: '" + e.getMessage() + "'");
                e.printStackTrace(System.out);
            }
        } catch (Exception e) {
            System.out.println("Framework not found");
            e.printStackTrace(System.out);
        }
    }

    /**
     * Gets the first found {@link Framework} implementation using the {@link ServiceLoader}
     *
     * @return framework implementation
     */
    private static Framework getFramework() {
        //System.out.println(ServiceLoader.load(Framework.class));

        return ServiceLoader.load(Framework.class)
                .stream()
                //.filter(f -> f.get().supports(Feature.TIMES))
                .map(ServiceLoader.Provider::get)
                .findAny()
                .orElseThrow();
    }
}
