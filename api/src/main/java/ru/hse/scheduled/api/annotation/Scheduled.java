package ru.hse.scheduled.api.annotation;

import ru.hse.scheduled.api.Feature;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * {@link ru.hse.scheduled.api.Framework} runs the methods with the {@link Scheduled} annotation at {@link #fixedRate()}.
 * <p>
 * The following is required to run the {@link Scheduled} method:
 * <ol>
 *     <li>The method annotated with the {@link Scheduled} annotation must accept no arguments</li>
 *     <li>The class containing the {@link Scheduled} method must have a constructor without parameters</li>
 *     <li>The parameterless constructor and the {@link Scheduled} method must be accessible through reflection
 *     to the framework implementation</li>
 * </ol>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Scheduled {
    /**
     * The fixed rate at which the {@link Scheduled} method must be run.
     * Must be {@code >= 0}
     *
     * @return the fixed rate
     */
    long fixedRate() default 1_000;

    /**
     * The number of times the {@link Scheduled} method must be run.
     * This support is optional, if the framework does not support {@code #times()} functionality, then if the
     * {@link ru.hse.scheduled.api.Framework} encounters a method with {@link Scheduled} annotation but with non-default
     * {@code times} value, then {@link IllegalStateException} is thrown.
     * <p>
     * To query if the {@link ru.hse.scheduled.api.Framework} supports this feature use the
     * {@link ru.hse.scheduled.api.Framework#supports(Feature)} method with {@link Feature#TIMES} argument
     *
     * @return number of times the given method must run
     */
    int times() default Integer.MAX_VALUE;
}
