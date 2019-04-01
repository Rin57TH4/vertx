package com.rin.vertx.code;

import io.netty.channel.Channel;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by duongittien
 */
public class NettyHelper {
    public static String ipAddress(Channel channel) {
        if (channel == null || !channel.isActive()) {
            return "";
        }
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        InetAddress inetaddress = socketAddress.getAddress();
        return inetaddress.getHostAddress();
    }

}
