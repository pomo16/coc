package com.pomo.coc.request;

import com.pomo.coc.controller.TestController;
import com.pomo.coc.util.JsonFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestService {

    @Autowired
    RestTemplate restTemplate;

    //coc接口请求token
    private static String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImQ3N2M5MzljLTRiMGItNDIzMi1iOTUyLThkNDM1YWYxMjA5ZSIsImlhdCI6MTU1OTAyMzU0OCwic3ViIjoiZGV2ZWxvcGVyL2U2YjZmYWJmLWYwZWYtMDMzNC0xZjNmLTBjMmI2NjkzMzkzZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjExMy4xMDIuMjM2LjE2NiJdLCJ0eXBlIjoiY2xpZW50In1dfQ.z713-WeK9IcNlamxbXFRHe1G3DgnflxabgiWty9634a-ZUB-zBEjpwKYCst5hDoCw7Oc2y4bxqD1piaIRdYuag";

    //URL
    public static String PLAYERS_RANK = "https://api.clashofclans.com/v1/locations/32000056/rankings/players?limit=20";
    public static String CLANS_RANK = "https://api.clashofclans.com/v1/locations/32000056/rankings/clans?limit=20";
    public static String PLAYER = "https://api.clashofclans.com/v1/players/%";
    public static String CLAN = "https://api.clashofclans.com/v1/clans/%";
    public static String TEST = "";

    /**
     * 向API请求数据
     */
    public String requestForData(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + TOKEN);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
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
     * 向API请求数据，并写入本地JSON，方便调试
     */
    public String requestForDataTest(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + TOKEN);
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
