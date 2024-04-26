package ru.hse.scheduled.impl_s;

import ru.hse.scheduled.api.Feature;
import ru.hse.scheduled.api.Framework;
import ru.hse.scheduled.api.annotation.Scheduled;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class New_Framework implements Framework {

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    public boolean supports(Feature feature) {
        return true;
    }

    @Override
    public void start(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Scheduled annotation = method.getAnnotation(Scheduled.class);
            if (annotation != null) {
                Object object = newInstance(clazz);
                long times = annotation.times();

                for (int i = 0; i < times; i++) {
                    executorService.schedule(
                            new PrintExceptionRunnable(() -> invokeMethod(method, object)), 1000 * i, TimeUnit.MILLISECONDS);
                }
                /*Runner exec = new Runner(times, () -> invokeMethod(method, object));
                var smt = executorService.scheduleAtFixedRate(exec, 0, 1000, TimeUnit.MILLISECONDS);
                exec.add(smt);
                */

            }
        }
    }

    private static void invokeMethod(Method method, Object object) {
        try {
            method.setAccessible(true);
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    private static Object newInstance(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {
        executorService.close();
    }

}
