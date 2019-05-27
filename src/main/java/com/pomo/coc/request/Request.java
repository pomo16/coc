package com.pomo.coc.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class Request {

    @Autowired
    RestTemplate restTemplate;

    private static String TOKEN = "";

    /**
     * 请求表格数据
     * @param "players" or "clans"
     * @return ranking table states, top 20 in China
     */
    public String getTableTask(String s) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + TOKEN);
        String url = "https://api.clashofclans.com/v1/locations/32000056/rankings/" + s + "?limit=20";
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
}
