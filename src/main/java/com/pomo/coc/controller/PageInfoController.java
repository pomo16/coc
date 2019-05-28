package com.pomo.coc.controller;

import com.pomo.coc.redis.RedisService;
import com.pomo.coc.redis.DataKey;
import com.pomo.coc.request.RequestService;
import com.pomo.coc.result.CodeMsg;
import com.pomo.coc.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageInfoController {

    @Autowired
    RequestService requestService;

    @Autowired
    RedisService redisService;

    @GetMapping("/createTable")
    @ResponseBody
    public Result<String> createTable(@RequestParam("type") String type) {
        String data = null;
        if (type.equals("players")) {
            data = redisService.get(DataKey.playersRankData, "", String.class);
            if (data == null || data.equals("false")) {
                data = requestService.requestForData(RequestService.PLAYERS_RANK);
                redisService.set(DataKey.playersRankData, "", data);
            }
        } else if (type.equals("clans")) {
            data = redisService.get(DataKey.clansRankData, "", String.class);
            if (data == null || data.equals("false")) {
                data = requestService.requestForData(RequestService.CLANS_RANK);
                redisService.set(DataKey.clansRankData, "", data);
            }
        }

        if (data.equals("false")) {
            return Result.error(CodeMsg.REQUEST_ERROR);
        }

        return Result.success(data);
    }

}
