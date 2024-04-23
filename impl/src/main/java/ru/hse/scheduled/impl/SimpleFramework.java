package ru.hse.scheduled.impl;

import ru.hse.scheduled.api.Framework;
import ru.hse.scheduled.api.annotation.Scheduled;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The simple {@link Framework} implementation
 */
public class SimpleFramework implements Framework {
    /**
     * {@link ScheduledExecutorService} to run the {@link Scheduled} methods
     */
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            Scheduled annotation = method.getAnnotation(Scheduled.class);
            if (annotation != null) {
                Object object = newInstance(clazz);
                long fixedRate = annotation.fixedRate();
                executorService.scheduleAtFixedRate(
                        new PrintExceptionRunnable(() -> invokeMethod(method, object)), 0, fixedRate, TimeUnit.MILLISECONDS);
            }
        }
    }

    /**
     * Invoke the given instance {@code method} on the given {@code object}
     *
     * @param method to invoke
     * @param object to invoke the method on
     * @throws IllegalStateException if unable to invoke the method
     */
    private static void invokeMethod(Method method, Object object) {
        try {
            method.setAccessible(true);
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Create a new instance of the given {@code clazz} using its default constructor
     *
     * @param clazz to create
     * @return new instance of the {@code clazz}
     * @throws IllegalStateException if unable to create instance of the {@code clazz}
     */
    private static Object newInstance(Class<?> clazz) {
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        executorService.close();
    }
}
