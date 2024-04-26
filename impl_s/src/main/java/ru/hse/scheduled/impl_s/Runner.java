package ru.hse.scheduled.impl_s;


import java.util.concurrent.ScheduledFuture;

public class Runner implements Runnable {
    private final long times;
    private long curTimes = 0;
    private final Runnable func;
    private ScheduledFuture<?> sch;

    public Runner(long times, Runnable func) {
        this.times = times;
        this.func = func;
    }

    @Override
    public void run() {
        synchronized (func) {
            func.run();
            curTimes++;

            System.out.println(curTimes);
            if (curTimes < times) return;

            sch.cancel(true);
        }
    }

    public void add(ScheduledFuture<?> sch) {
        this.sch = sch;
    }

}
