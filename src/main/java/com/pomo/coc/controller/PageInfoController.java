package com.pomo.coc.controller;

import com.pomo.coc.task.RequestTask;
import com.pomo.coc.util.JedisComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageInfoController {

    @Autowired
    JedisComponent jedis;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/clans")
    public String clans() {
        return "clans";
    }

    @RequestMapping("/players")
    public String players() {
        return "players";
    }

    @RequestMapping("/making")
    public String making() {
        return "making";
    }

    @GetMapping("/createTable")
    @ResponseBody
    public String createTable(@RequestParam("type") String type) {
        return jedis.get(type);
    }

}