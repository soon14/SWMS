package com.swms.gateway.auth.verify;

import com.auth0.jwt.interfaces.Clock;

import java.util.Date;

/**
 * @author 斯蒂文
 * @date 2022年07月05日 19:57
 */
public class ClockImpl implements Clock {
    public ClockImpl() {
    }

    @Override
    public Date getToday() {
        return new Date();
    }
}
