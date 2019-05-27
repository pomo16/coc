package com.pomo.coc.task;

import com.pomo.coc.util.JedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 运行定时任务，周期性请求API获取排行榜数据，并写入redis缓存
 */
@Component
@EnableScheduling
public class RunTask {

    @Autowired
    JedisComponent jedis;

    /**
     * 15分钟请求一次玩家数据，因为玩家数据变更更加频繁
     */
    //@Scheduled(initialDelay=1000, fixedRate = 1000 * 60 * 20)
    @Async
    public void runFixedRate1() {
        RequestTask task = new RequestTask();
        System.err.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        String result = task.getTableTask("players");
        if (!result.equals("false")){
            jedis.set("players", result);
        }
    }

    /**
     * 60分钟请求一次部落数据，因为部落数据变更相对较少
     */
    //@Scheduled(initialDelay=1000, fixedRate = 1000 * 60 * 60)
    @Async
    public void runFixedRate2() {
        RequestTask task = new RequestTask();
        System.err.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        String result = task.getTableTask("clans");
        if (!result.equals("false")){
            jedis.set("clans", result);
        }
    }
}


