package ru.hse.scheduled.impl_s;


import java.util.concurrent.ScheduledFuture;

public class Executor implements Runnable {
    private final long times;
    long cur_times = 0;
    private final Runnable func;
    private ScheduledFuture<?> sch;

    public Executor(long times, Runnable func) {
        this.times = times;
        this.func = func;
    }

    @Override
    public void run() {
        synchronized (func) {
            func.run();
            cur_times++;

            System.out.println(cur_times);
            if (cur_times < times) return;

            sch.cancel(true);
        }
    }

    public void add(ScheduledFuture<?> sch) {
        this.sch = sch;
    }

}
