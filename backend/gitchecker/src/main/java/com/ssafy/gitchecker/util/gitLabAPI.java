package com.ssafy.gitchecker.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class gitLabAPI {
    
    private static final String BaseUrl = "https://lab.ssafy.com/api/v4";
    private static final String privateToken = "a3sroJgsz_iuJxeT9-Mj";

    public ResponseEntity<String> callAPI(String url, Map<String, String> params){
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(5000);
        factory.setConnectTimeout(3000);

        RestTemplate rt = new RestTemplate(factory);

        HttpHeaders headers = new HttpHeaders();
        headers.add("PRIVATE-TOKEN", privateToken);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(BaseUrl + url);
        params.forEach( (key, value) -> {   
            builder.queryParam(key, value);
        });

        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);

        return rt.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);
    }

    public Map<String, String> getProjects(Map<String, String> params){
        ResponseEntity<String> responseEntity = callAPI("/project", params);

        Gson gson = new Gson();
        JsonArray jsonArr = gson.fromJson(responseEntity.getBody(), JsonArray.class);

        Map<String, String> resMap = new HashMap<>();
        
        for(int i = 0; i < jsonArr.size(); i++){
            resMap.put( i + "", jsonArr.get(i).getAsJsonObject().get("path").toString());
        }

        return resMap;
    }
}
