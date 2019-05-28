package com.pomo.coc.redis;

public class DataKey extends BasePrefix{

    private DataKey(String prefix) {
        super(prefix);
    }

    public static DataKey playersRankData = new DataKey("prd");
    public static DataKey clansRankData = new DataKey("crd");
    public static DataKey playerSearchData = new DataKey("psd");
    public static DataKey clanSearchData = new DataKey("csd");

}
