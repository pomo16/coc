package com.pomo.coc.task;

import com.pomo.coc.redis.DataKey;
import com.pomo.coc.redis.RedisService;
import com.pomo.coc.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 运行定时任务，周期性请求API获取排行榜数据，并写入redis缓存
 */
@Service
@EnableScheduling
public class RunTask {

    @Autowired
    RedisService redisService;

    @Autowired
    RequestService requestService;

    /**
     * 15分钟请求一次玩家数据，因为玩家数据变更更加频繁
     */
    @Scheduled(initialDelay=1000, fixedRate = 1000 * 60 * 20)
    @Async
    public void runFixedRate1() {
        System.err.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        String data = requestService.requestForData(RequestService.PLAYERS_RANK);
        if (!data.equals("false")){
            redisService.set(DataKey.playersRankData, "", data);
        }
    }

    /**
     * 60分钟请求一次部落数据，因为部落数据变更相对较少
     */
    @Scheduled(initialDelay=1000, fixedRate = 1000 * 60 * 60)
    @Async
    public void runFixedRate2() {
        System.err.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        String data = requestService.requestForData(RequestService.CLANS_RANK);
        if (!data.equals("false")){
            redisService.set(DataKey.clansRankData, "", data);
        }
    }
}


