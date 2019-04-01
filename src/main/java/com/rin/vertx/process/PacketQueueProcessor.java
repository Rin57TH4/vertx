package com.rin.vertx.process;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by duongittien
 */
public class PacketQueueProcessor {
    public static final LinkedBlockingQueue<PacketQueue> packetQueue = new LinkedBlockingQueue<>();
}
