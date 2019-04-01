package com.rin.vertx.code.network;

import com.rin.vertx.Main;
import com.rin.vertx.code.entities.Packet;
import com.rin.vertx.code.entities.PacketImpl;
import com.rin.vertx.code.entities.ServerErrorObj;
import com.rin.vertx.code.entities.response.ServerResponse;
import com.rin.vertx.code.network.error.ServerError;
import com.rin.vertx.code.network.session.Session;
import com.rin.vertx.code.network.session.SessionImpl;
import com.rin.vertx.code.network.session.SessionState;
import com.rin.vertx.common.ApiCodeConfig;
import com.rin.vertx.common.Common;
import com.rin.vertx.common.CommonFlagConfig;
import com.rin.vertx.manager.SessionManager;
import com.rin.vertx.process.PacketQueue;
import com.rin.vertx.process.PacketQueueProcessor;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.vertx.core.Vertx;

/**
 * Created by duongittien
 */
public class PacketChannelHandler extends SimpleChannelInboundHandler<Packet> {
    private final Vertx vertx;
    protected final Session session;

    public PacketChannelHandler(Vertx vertx, int sessionType) {
        super();
        this.vertx = vertx;
        session = new SessionImpl();
        session.setSessionType(sessionType);
        SessionManager.getInstance().addSession(session.getSessionId(), session);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
        //channel
        session.setLastUpdateTime(System.currentTimeMillis());
        if (Main.IS_MAINTAIN) {
            packet.setContent(ServerResponse.buildError(new ServerErrorObj(new ServerError(1, "Hệ thống đang bảo trì, vui lòng quay lại sau"))));
            session.send(packet);
            return;
        }

        String key=packet.getCode();
        if (!session.validateSession(key)) {
            return;
        }
        PacketQueue queue = new PacketQueue(vertx, session, packet);
        PacketQueueProcessor.packetQueue.offer(queue);
        System.out.printf("channelRead0 {}", packet);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        session.setChannel(ctx.channel());
        sendCRT();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.printf("ChannelInactive sessionId: {}", session.getSessionId());
        session.setSessionState(SessionState.CLOSED);

        SessionManager.getInstance().removeSession(session.getSessionId(), Common.USER_DISCONNECT_NORMAL);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        String name = cause.getClass().getSimpleName();
        String uid = session != null && session.getUser() != null ? session.getUser().getUserId() : "unknown";
        String ip = session != null ? session.getRemoteIp() : "unknown";
        System.out.printf("exceptionCaught{} ", cause);
//        String exceptions = AppConfig.getConfig().getProperty("EXCEPTION", "", "SETTINGS");
        String exceptions = "";
        if (!"".equals(exceptions)) {
            String[] exs = exceptions.split(",");
            for (String ex : exs) {
                if (ex.equalsIgnoreCase(name)) {
                    ctx.close();
                }
            }
        }
//        logger.info("exceptionCaught: {}, user={}, ip={} reason = {}", name, uid, ip, cause);
//        logger.error("exceptionCaught: ", cause);
    }

    void sendCRT() {
        String created = "CREATED";
        Packet packet = new PacketImpl(CommonFlagConfig.CRT, ApiCodeConfig.CRT, ApiCodeConfig.Type.Type0.getByte(), Common.SERVER_SENDER, ServerResponse.build(true, created));
        ChannelFuture channelFuture = session.send(packet);
        System.out.printf("channelFuture {}", channelFuture);
    }
}
