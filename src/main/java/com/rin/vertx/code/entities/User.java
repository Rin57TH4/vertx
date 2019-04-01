package com.rin.vertx.code.entities;

import com.rin.vertx.code.network.session.Session;

/**
 * Created by duongittien
 */
public interface User {
    Session getSession();

    void setSession(Session session);

    String getUserId();

    void setUserId(String userId);

    String getUserName();

    void setUserName(String userName);

    String getDisplayName();

    void setDisplayName(String displayName);

    String getAvatar();

    void setAvatar(String avatar);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);

    Long getCreated();

    void setCreated(Long created);

    Long getTotalTrans();

    void setTotalTrans(Long totalTrans);

    Integer getUserType();

    void setUserType(Integer userType);

    Integer getUserGroup();

    void setUserGroup(Integer userGroup);

    Long getMoney();

    void setMoney(Long money);

    Long getLevel();

    void setLevel(Long level);

    Long getVip();

    void setVip(Long vip);

    Long getExp();

    void setExp(Long exp);

    int getLoginType();

    void setLoginType(int loginType);

    String getIp();

    void setIp(String ip);

    String getStringVariable(String var);

    void setStringVariable(String key, String value);

    int getIntVariable(String var);

    void setIntVariable(String key, int value);

    double getDoubleVariable(String var);

    void setDoubleVariable(String key, double value);

    long getLongVariable(String var);

    void setLongVariable(String key, long value);

    long getLastRoom();

    void setLastRoom(long roomId);
}
