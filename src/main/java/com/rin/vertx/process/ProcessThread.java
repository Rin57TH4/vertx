package com.rin.vertx.process;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by duongittien
 */
public class ProcessThread extends Thread {
    //    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    public ProcessThread(String name) {
        super(name);
    }

    public void enqueue(Runnable cmd) {
        if (!queue.offer(cmd)) {
            System.out.println("Can not put cmd into queue");
//            logger.info("Can not put cmd into queue");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Runnable cmd = queue.poll(5, TimeUnit.SECONDS);
                if (cmd != null) {
                    cmd.run();
                }
            } catch (InterruptedException e) {
                System.out.printf("Err InterruptedException {}", e);
            } catch (Throwable ex) {
                System.out.printf("Err {}", ex);
//                logger.error("Exception: ", ex);
            }
        }
    }
}
