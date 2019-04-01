package com.rin.vertx.code.network.session;

import com.rin.vertx.code.codec.EntityByteArrayConverter;
import com.rin.vertx.code.entities.CommandFlag;
import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.entities.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

public interface Session {
//    ApiBlockByTime checkBlockApi(String code, byte type);

    Channel getChannel();

    SessionState getSessionState();

    boolean allowPingPong();

    void setSessionState(SessionState sessionState);

//    void notifySessionFailure(ErrorResponse error);

    ChannelFuture send(CommandFlag flag, String code, byte type, int senderId, EntityByteArrayConverter obj);

    ChannelFuture send(Packet packet);

    void notifyUpdateBalance();

    void notifyMessage(String message);

    boolean isConnected();

    long getCreatedTime();

    void preparePacketReceived(Packet packet);

    boolean validateSession(String key);

    boolean expired();

    void setChannel(Channel channel);

    String getSessionId();

    void setSessionId(String sessionId);

    User getUser();

    void setUser(User user);

    void setRoomId(int roomId);

    void updateUserMoney(User user);

    String getRemoteIp();

    void setCrtCheckingTime();

    long getCrtCheckingTime();

    boolean shouldBlock(String code, int type);

    long getId();

    void updateCloseTime();

    int getSessionType();

    void setSessionType(int sessionType);

    int getLastRoomId();

    void setLastUpdateTime(long time);

    long getLastUpdateTime();
}
