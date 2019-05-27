package com.pomo.coc.controller;

import com.pomo.coc.util.JsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Controller
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search")
    @ResponseBody
    public String search(@RequestParam("type") String type, @RequestParam("id") String id) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjMyOGNkMmU2LTAwMDYtNDgxYi04Y2Q3LTBiZjk4YTRhZjNkNSIsImlhdCI6MTU1NzYyODQzMywic3ViIjoiZGV2ZWxvcGVyL2U2YjZmYWJmLWYwZWYtMDMzNC0xZjNmLTBjMmI2NjkzMzkzZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjIyMi4yMDAuMTExLjE3NCJdLCJ0eXBlIjoiY2xpZW50In1dfQ.frZWDMuFU6v640L0Vxe2y_BWa_ejX2tG7elK8L-Nj0oMY_3OXX82rjyWcWY8Qqc8hLy8z-_opJZvzo-IUP1snw";
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + token);
        String url = "https://api.clashofclans.com/v1/" + type + "/%" + id;

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
}
