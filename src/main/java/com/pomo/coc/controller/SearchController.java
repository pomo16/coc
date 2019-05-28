package com.pomo.coc.controller;

import com.pomo.coc.redis.DataKey;
import com.pomo.coc.redis.RedisService;
import com.pomo.coc.request.RequestService;
import com.pomo.coc.result.CodeMsg;
import com.pomo.coc.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    RequestService requestService;

    @Autowired
    RedisService redisService;

    @GetMapping("/clan")
    @ResponseBody
    public Result<String> searchClan(@RequestParam("id") String id) {
        String data = redisService.get(DataKey.clanSearchData, ""+id, String.class);
        if (data == null || data.equals("false")) {
            data = requestService.requestForData(RequestService.CLAN + id);
            redisService.set(DataKey.clanSearchData, ""+id, data);
        }
        if (data.equals("false")) {
            Result.error(CodeMsg.REQUEST_ERROR);
        }
        return Result.success(data);
    }

    @GetMapping("/player")
    @ResponseBody
    public Result<String> searchPlayer(@RequestParam("id") String id) {
        String data = redisService.get(DataKey.playerSearchData, ""+id, String.class);
        if (data == null || data.equals("false")) {
            data = requestService.requestForData(RequestService.PLAYER + id);
            redisService.set(DataKey.playerSearchData, ""+id, data);
        }
        if (data.equals("false")) {
            Result.error(CodeMsg.REQUEST_ERROR);
        }
        return Result.success(data);
    }
}
