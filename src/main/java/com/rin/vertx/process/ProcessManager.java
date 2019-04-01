package com.rin.vertx.process;

import com.rin.vertx.code.network.session.Session;

/**
 * Created by duongittien
 */
public class ProcessManager {

    private ProcessThread[] lstprocessor;

    private int processorsize;

    private ProcessThread[] othersLstprocessor;

    private int othersProcessorsize;

    private static ProcessManager instance;

    private ProcessManager() {
        init();
    }

    private void init() {
        processorsize = 20;
        lstprocessor = new ProcessThread[processorsize];
        othersProcessorsize = processorsize / 2;
        othersLstprocessor = new ProcessThread[othersProcessorsize];
        for (int i = 0; i < processorsize; i++) {
            ProcessThread thread = new ProcessThread("M-Process-" + i);
            thread.start();
            lstprocessor[i] = thread;
        }
        for (int i = 0; i < othersProcessorsize; i++) {
            ProcessThread thread = new ProcessThread("O-Process-" + i);
            thread.start();
            othersLstprocessor[i] = thread;
        }
    }

    public void enqueue(Session user, Runnable cmd, boolean isOther) {
        if (isOther) {
            long index = 0;
            if (user != null) {
                index = user.getId() % othersProcessorsize;
            } else {
                System.out.println("User is null, index processor: 0");
//                logger.info("User is null, index processor: 0");
            }
            othersLstprocessor[(int) index].enqueue(cmd);
            System.out.printf("System put cmd into processor: {} of Charging Processor!!!", index);
//            logger.info("System put cmd into processor: {} of Charging Processor!!!", index);
        } else {
            enqueue(user, cmd);
        }
    }

    public void enqueue(Session user, Runnable cmd) {
        long index = 0;
        if (user != null) {
            index = user.getId() % processorsize;
        }
        lstprocessor[(int) index].enqueue(cmd);
    }

    public static synchronized ProcessManager getInstance() {
        if (instance == null) {
            instance = new ProcessManager();
        }
        return instance;
    }
}
