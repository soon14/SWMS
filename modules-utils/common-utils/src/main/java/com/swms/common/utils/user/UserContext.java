package com.swms.common.utils.user;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserContext {

    private UserContext() {
    }

    public static final ThreadLocal<String> USER = new ThreadLocal<>();

    /**
     * 获取当前用户名
     *
     * @return
     */
    public static String getCurrentUser() {
        return USER.get();
    }

    public static void setUserName(String username) {
        USER.set(username);
    }

    public static void removeUserName() {
        USER.remove();
    }
}
