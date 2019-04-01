package com.rin.vertx.code.network.session;

import com.rin.vertx.code.NettyHelper;
import com.rin.vertx.code.ServerMessage;
import com.rin.vertx.code.codec.EntityByteArrayConverter;
import com.rin.vertx.code.entities.*;
import com.rin.vertx.code.entities.response.ServerResponse;
import com.rin.vertx.common.ApiCodeConfig;
import com.rin.vertx.common.Common;
import com.rin.vertx.common.CommonFlagConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by duongittien
 */
public class SessionImpl implements Session {
    //    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final List<String> sessionNotChecks = Arrays.asList(
            ApiCodeConfig.CRT,
            ApiCodeConfig.PIP,
            ApiCodeConfig.LGI);
    private static final AtomicLong idGenerator = new AtomicLong(0);
    private Channel channel;
    private ObjectId objectId;
    private String sessionId;
    private long id;
    private long closedTime;
    private long created;
    private long crtCheckingTime;
    private int sessionType;
    private int roomId;
    private SessionState sessionState;
    private String remoteIp;
    protected User user;
    private long lastUpdateTime;

    public SessionImpl() {
        this.sessionType = SessionType.SOCKET;
        this.id = idGenerator.getAndIncrement();
        this.objectId = new ObjectId();
        this.sessionId = objectId.toHexString();
        this.sessionState = SessionState.AUTHENTICATION_KEY;
        this.created = System.currentTimeMillis();
        this.lastUpdateTime = System.currentTimeMillis();
    }


    @Override
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public SessionState getSessionState() {
        return this.sessionState;
    }

    @Override
    public boolean allowPingPong() {
        return this.sessionState == SessionState.READY || this.sessionState == SessionState.AUTHORIZED;
    }

    @Override
    public void setSessionState(SessionState sessionState) {
        if (this.sessionState != sessionState) {
            this.sessionState = sessionState;
            if (sessionState == SessionState.CLOSED) {
                synchronized (objectId) {
                    objectId.setTimestamp(ObjectId.dateToTimestampSeconds(new Date()));
                }
                closedTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public ChannelFuture send(CommandFlag flag, String code, byte type, int senderId, EntityByteArrayConverter obj) {
        if (channel != null && channel.isActive()) {
            Packet packet = new PacketImpl(flag, code, type, senderId, obj);
            return send(packet);
        }
        return null;
    }

    @Override
    public ChannelFuture send(Packet packet) {
        if (channel != null && channel.isActive()) {
            preparePacketToSend(packet);
            return channel.writeAndFlush(packet);
        }
        return null;
    }

    @Override
    public void notifyUpdateBalance() {

    }

    @Override
    public void notifyMessage(String message) {
        send(new PacketImpl(CommonFlagConfig.PASSIVE, ApiCodeConfig.SYS, ApiCodeConfig.Type.Type99.getByte(), Common.SERVER_SENDER, ServerResponse.build(true, new ServerMessage(message))));
    }

    @Override
    public boolean isConnected() {
        return channel != null && channel.isActive() && getSessionState() == SessionState.AUTHORIZED;
    }

    @Override
    public long getCreatedTime() {
        return this.created;
    }

    @Override
    public void preparePacketReceived(Packet packet) {
        boolean crypt = packet.getCommandFlag().isCrypt();
        boolean compress = packet.getCommandFlag().isCompress();
    }

    @Override
    public boolean validateSession(String key) {
        if (this.sessionState == SessionState.AUTHENTICATION_KEY) {
            return key.equals(ApiCodeConfig.CRT);
        } else if (this.sessionState == SessionState.READY) {
            return sessionNotChecks.contains(key);
        } else {
            return this.sessionState == SessionState.AUTHORIZED;
        }
    }

    @Override
    public boolean expired() {
        return System.currentTimeMillis() - closedTime > SessionConfig.EXPRIRE_TIME;
    }

    @Override
    public void setChannel(Channel channel) {
        this.channel = channel;
        remoteIp = NettyHelper.ipAddress(channel);
    }

    @Override
    public String getSessionId() {
        return this.sessionId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void updateUserMoney(User user) {

    }

    @Override
    public String getRemoteIp() {
        return this.remoteIp;
    }

    @Override
    public void setCrtCheckingTime() {
        this.crtCheckingTime = System.currentTimeMillis();
    }

    @Override
    public long getCrtCheckingTime() {
        return this.crtCheckingTime;
    }

    @Override
    public boolean shouldBlock(String code, int type) {
        return false;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public void updateCloseTime() {
        this.closedTime = System.currentTimeMillis();
    }

    @Override
    public int getSessionType() {
        return this.sessionType;
    }

    @Override
    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    @Override
    public int getLastRoomId() {
        return this.roomId;
    }

    @Override
    public void setLastUpdateTime(long time) {
        this.lastUpdateTime = time;
    }

    @Override
    public long getLastUpdateTime() {
        return this.getLastUpdateTime();
    }

    private void preparePacketToSend(Packet packet) {
        boolean crypt = packet.getCommandFlag().isCrypt();
        boolean compress = packet.getCommandFlag().isCompress();
    }

    @Override
    public String toString() {
        return "SessionImpl{" +
                ", id=" + id +
//                ", objectId=" + objectId +
                ", sessionState=" + sessionState +
                ", channel=" + channel +
                ", sessionId='" + sessionId + '\'' +
                ", user=" + user +
                ", closedTime=" + closedTime +
                ", created=" + created +
                ", remoteIp='" + remoteIp + '\'' +
                ", crtCheckingTime=" + crtCheckingTime +
                ", sessionType=" + sessionType +
                ", roomId=" + roomId +
                '}';
    }
}
