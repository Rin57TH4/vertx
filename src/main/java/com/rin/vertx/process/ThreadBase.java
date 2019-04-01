package com.rin.vertx.process;

import lombok.Getter;

/**
 * Created by duongittien
 */
public abstract class ThreadBase {
    private Thread t;
    @Getter
    private String name;
    protected boolean terminate;
    protected boolean sleeping = false;
    private static final int BEFORE_START = 111;
    private static final int AFTER_START = 222;
    private static final int BEFORE_STOP = 333;
    private static final int AFTER_STOP = 444;
    private static final int START = 555;
    public static final int NO_SLEEP = -1;

    public ThreadBase(String name) {
        this.name = name;
        t = createThread();
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public void setTerminate(boolean terminate) {
        this.terminate = terminate;
    }

    public void terminate() {
        if (terminate)
            return;
        try {
            terminate = true;
            executeLifecycleMethod(BEFORE_STOP);
            if (isAlive() && !t.interrupted()) {
                if (t.getState() != Thread.State.NEW)
                    t.join();
            }
            executeLifecycleMethod(AFTER_STOP);
        } catch (InterruptedException ex) {
            onException(ex);
        }

    }

    public void execute() {
        terminate = false;
        switch (t.getState()) {
            case NEW:
                startup();
                break;
            case TERMINATED:
                t = createThread();
                startup();
                break;
        }
    }

    private void startup() {
        executeLifecycleMethod(BEFORE_START);
        terminate = false;
        executeLifecycleMethod(START);
        executeLifecycleMethod(AFTER_START);
    }

    public boolean isAlive() {
        return t == null ? false : t.isAlive();
    }

    private Thread createThread() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                while (!terminate) {
                    long milis;
                    try {
                        onExecuting();
                        doWork();
                        if (!terminate) {
                            milis = sleeptime();
                            if (NO_SLEEP != milis) {
                                sleep(milis);
                            }
                        }
                    } catch (Exception e) {
                        onException(e);
                    }
                }
                terminate();
                onKilling();
            }
        }, this.name);
    }

    private void sleep(long milliseconds) {
        try {
            sleeping = true;
            t.sleep(milliseconds);
        } catch (InterruptedException ex) {
            onException(ex);
        } finally {
            sleeping = false;
        }
    }

    private void executeLifecycleMethod(final int pState) {
            switch (pState) {
                case START:
                    t.start();
                    break;
                case BEFORE_START:
                    beforeStart();
                    break;
                case AFTER_START:
                    afterStart();
                    break;
                case BEFORE_STOP:
                    beforeStop();
                    break;
                case AFTER_STOP:
                    afterStop();
                    break;
            }
    }

    protected abstract long sleeptime() throws Exception;

    protected abstract void onExecuting() throws Exception;

    protected abstract void onKilling();

    protected abstract void onException(Exception e);

    protected abstract void doWork() throws InterruptedException;

    protected abstract void beforeStart();

    protected abstract void afterStart();

    protected abstract void beforeStop();

    protected abstract void afterStop();

}
