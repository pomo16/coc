package com.pomo.coc.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class RequestTask {

    @Autowired
    private RestTemplate restTemplate;

    public static RequestTask requestTask;

    @PostConstruct
    public void init() {
        requestTask = this;
        requestTask.restTemplate = this.restTemplate;
    }

    /**
     * 请求表格数据
     * @param "players" or "clans"
     * @return ranking table states
     */
    public String getTableTask(String s) {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjEzZDgxZGM3LWY4ZTItNDMxYy1hOWY5LTQxOTg3ZDgwNjUxMiIsImlhdCI6MTU1NzUwMTc5OCwic3ViIjoiZGV2ZWxvcGVyL2U2YjZmYWJmLWYwZWYtMDMzNC0xZjNmLTBjMmI2NjkzMzkzZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjE4My4yMzYuMTkuMTg4Il0sInR5cGUiOiJjbGllbnQifV19.vtotRPYXu_E_8hOZ3BQp0SOCMPdoLwFuSnygWRMOrdj0Ohx0Ve5KpV1iK5RI0Ej91IbXRpG23gfhAWK4u2nOqQ";
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + token);
        String url = "https://api.clashofclans.com/v1/locations/32000056/rankings/" + s + "?limit=20";
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        try {
            ResponseEntity<String> response = requestTask.restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
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
