package com.rin.vertx.common.utils;

import com.rin.vertx.code.entities.User;
import com.rin.vertx.code.network.session.Session;

/**
 * Created by duongittien
 */
public class AppUtils {
    public static String getUsername(Session session) {
        if (session == null) {
            return "";
        }
        User user = session.getUser();
        if (user == null) {
            return "";
        }
        return user.getUserName();
    }

//    public static RoomResp buildRoomResp(Room r) {
//        RoomResp rs = new RoomResp();
//        rs.setRoomId(r.getRoomId());
//        rs.setRoomName(r.getName());
//        rs.setProtected(r.isProtected());
//        rs.setRoomType(r.getRoomType());
//        rs.setMaxPlayer(r.getMaxPlayer());
//        rs.setPlayer(r.getListUser().size());
//        int playing = GameRoom.GAME_TABLE_STATUS_FINISH;
//        if (r.containsVariable(Common.GAME_PLAYING)) {
//            playing = r.getIntVariable(Common.GAME_PLAYING);
//        }
//        rs.setPlaying(playing);
//        return rs;
//    }

    public static String parseString(Object o) {
        if (o == null) {
            return "";
        }
        return String.valueOf(o);
    }

    public static int parseInt(Object o) {
        if (o == null) {
            return 0;
        }
        try {
            return Integer.parseInt(String.valueOf(o));
        } catch (Exception ex) {
            return 0;
        }
    }

    public static double parseDouble(Object o) {
        if (o == null) {
            return 0;
        }
        try {
            return Double.parseDouble(String.valueOf(o));
        } catch (Exception ex) {
            return 0;
        }
    }

    public static long parseLong(Object o) {
        if (o == null) {
            return 0;
        }
        try {
            return Long.parseLong(String.valueOf(o));
        } catch (Exception ex) {
            return 0;
        }
    }

}
