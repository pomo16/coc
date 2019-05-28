package com.pomo.coc.redis;

public class DataKey extends BasePrefix{

    private DataKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static DataKey playersRankData = new DataKey(0, "prd");
    public static DataKey clansRankData = new DataKey(0, "crd");
    public static DataKey playerSearchData = new DataKey(10 * 60, "psd");
    public static DataKey clanSearchData = new DataKey(10 * 60, "csd");

}
