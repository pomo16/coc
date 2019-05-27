package com.pomo.coc.controller;

import com.pomo.coc.util.JsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;



@Controller
public class TestController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 测试方法
     */
    @RequestMapping("/test")
    @ResponseBody
    public String test(@RequestParam("id") String id) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImE0Y2U2NGE5LTMzMTgtNDFhNC05ODMzLTkwOWRlODYwNGI5YSIsImlhdCI6MTU1NzYyNjc2NSwic3ViIjoiZGV2ZWxvcGVyL2U2YjZmYWJmLWYwZWYtMDMzNC0xZjNmLTBjMmI2NjkzMzkzZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE4My4yMzYuMTkuMTkzIl0sInR5cGUiOiJjbGllbnQifV19.1mmOWiCmTwFHQOkVjzQwDpC16Zc3SeDVuaxte--FDQZdakbFEso-z62DzWVdtbQMqolXIXby1avcEy7NkkEqTw";
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + token);
        String url = "https://api.clashofclans.com/v1/players/%" + id;

        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            String path = TestController.class.getClassLoader().getResource("test.json").getPath();
            JsonFile.writeJsonFile(path, response.getBody());
            System.out.println(response.getBody());
            return response.getBody();
        }
        catch (HttpClientErrorException e) {
            //捕捉HTTP异常
            System.out.println(e.getResponseBodyAsString());
            return "false";
        }
        catch (Exception e) {
            //捕捉所有异常
            e.printStackTrace();
            return "false";
        }
    }

    /**
     * 读取本地JSON文件
     */
    @RequestMapping("/json")
    @ResponseBody
    public String json() {
        String path = TestController.class.getClassLoader().getResource("test.json").getPath();
        return JsonFile.readJsonFile(path);
    }

    @RequestMapping("/getTest")
    public String getTest() {
        return "test";
    }
}
