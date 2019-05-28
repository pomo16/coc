package com.pomo.coc.controller;

import com.pomo.coc.request.RequestService;
import com.pomo.coc.result.Result;
import com.pomo.coc.util.JsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RequestService requestService;

    /**
     * 读取本地JSON文件
     */
    @RequestMapping("/json")
    @ResponseBody
    public String json(@RequestParam("name") String name) {
        String path = TestController.class.getClassLoader().getResource(name + ".json").getPath();
        return JsonFile.readJsonFile(path);
    }

    /**
     * 测试接口
     */
    @RequestMapping("/test")
    @ResponseBody
    public Result<String> test(@RequestParam("id") String id) {
        String url = RequestService.CLAN + id;
        requestService.requestForDataTest(url);
        return Result.success("good");
    }
}
