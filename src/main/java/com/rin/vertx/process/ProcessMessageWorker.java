package com.rin.vertx.process;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by duongittien
 */
public class ProcessMessageWorker implements Runnable {
    private final ExecutorService executor;

    public ProcessMessageWorker(ExecutorService pool) {
        this.executor = pool;
    }

    @Override
    public void run() {
        while (true) {
//            PacketQueueProcessor.packetQueue.poll();
            try {
                PacketQueue packet = PacketQueueProcessor.packetQueue.poll(5, TimeUnit.SECONDS);
                if (packet != null) {
                    Runnable readProcess = new ExecuteMessageProcess(packet.getVertx(), packet.getSession(), packet.getPacket());
                    executor.execute(readProcess);
                }
            } catch (Throwable ex) {
                System.out.printf("err {}", ex);
//                logger.error("", ex);
            }
        }
    }
}
