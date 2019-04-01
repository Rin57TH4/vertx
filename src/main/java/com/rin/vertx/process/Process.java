package com.rin.vertx.process;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by duongittien
 */
public class Process extends ThreadBase {
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    public Process(String name) {
        super(name);
    }

    public void enqueue(Runnable cmd) {
        if (!queue.offer(cmd)) {
            System.out.println("Can not put cmd into queue");
//            logger.info("Can not put cmd into queue");
        }
    }

    @Override
    protected long sleeptime() throws Exception {
        return 10;
    }

    @Override
    protected void onExecuting() throws Exception {
        System.out.printf(this.getName() + "onExecuting ...");
    }

    @Override
    protected void onKilling() {
        System.out.printf(this.getName() + "onKilling ...");
    }

    @Override
    protected void onException(Exception e) {
        System.out.printf(this.getName() + "onException ... {}", e);
    }

    @Override
    protected void doWork() throws InterruptedException {
        Runnable cmd = queue.poll(5, TimeUnit.SECONDS);
        if (cmd != null) {
            cmd.run();
        }
    }

    @Override
    protected void beforeStart() {
        System.out.printf(this.getName() + "beforeStart ...");
    }

    @Override
    protected void afterStart() {
        System.out.printf(this.getName() + "afterStart ...");
    }

    @Override
    protected void beforeStop() {
        System.out.printf(this.getName() + "beforeStop ...");
    }

    @Override
    protected void afterStop() {
        System.out.printf(this.getName() + "afterStop ...");
    }
}
