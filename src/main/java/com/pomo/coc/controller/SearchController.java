package com.pomo.coc.controller;

import com.pomo.coc.request.RequestService;
import com.pomo.coc.result.CodeMsg;
import com.pomo.coc.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/search")
    @ResponseBody
    public Result<String> search(@RequestParam("type") String type, @RequestParam("id") String id) {
        String url = null;
        if(type.equals("clans")) {
            url = RequestService.CLAN + id;
        } else if (type.equals("players")) {
            url = RequestService.PLAYER + id;
        }

        if(url == null) {
            return Result.error(CodeMsg.REQUEST_ERROR);
        }

        return Result.success(requestService.requestForData(url));
    }
}
