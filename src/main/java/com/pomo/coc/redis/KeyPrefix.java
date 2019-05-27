package com.pomo.coc.redis;

public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
