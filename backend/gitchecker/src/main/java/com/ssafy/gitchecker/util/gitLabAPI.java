package com.ssafy.gitchecker.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.gitchecker.model.Student;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GitLabAPI {
    
    private static final String BaseUrl = "https://lab.ssafy.com/api/v4";
    private static final String privateToken = "a3sroJgsz_iuJxeT9-Mj";

    private ResponseEntity<String> callAPI(String url){
        return callAPI(url, new HashMap<>());
    }

    private ResponseEntity<String> callAPI(String url, Map<String, String> params){
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
        ResponseEntity<String> responseEntity = callAPI("/projects", params);

        Gson gson = new Gson();
        JsonArray jsonArr = gson.fromJson(responseEntity.getBody(), JsonArray.class);

        Map<String, String> resMap = new HashMap<>();
        
        for(int i = 0; i < jsonArr.size(); i++){
            resMap.put( jsonArr.get(i).getAsJsonObject().get("id").toString(), jsonArr.get(i).getAsJsonObject().get("path").toString());
        }

        return resMap;
    }

    public Map<String, Student> getMembers(String projectID){
        ResponseEntity<String> responseEntity = callAPI("/projects/"+ projectID +"/members");

        JsonArray jsonArr = new Gson().fromJson(responseEntity.getBody(), JsonArray.class);

        Map<String, Student> resMap = new HashMap<>();

        for(int i = 0; i < jsonArr.size(); i++){
            Student student = new Student();
            JsonObject jo = jsonArr.get(i).getAsJsonObject();
            student.setName(jo.get("name").toString());
            student.setId(Long.parseLong(jo.get("id").toString()));
            student.setGitId(jo.get("username").toString());
            student.setImageUrl(jo.get("avatar_url").toString());

            resMap.put(jo.get("id").toString(), student);
        }

        return resMap;
    }

    public String getCity(char c){
        String res = "구미";

        if(c == 'a' || c == 'A') res = "서울";
        else if(c == 'b' || c == 'B') res = "대전";
        else if(c == 'c' || c == 'C') res = "광주";

        return res;
    }
}
