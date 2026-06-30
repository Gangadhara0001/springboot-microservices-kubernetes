package com.example.helper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplteHelper {

    private final RestTemplate restTemplate;

    public RestTemplteHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T>T post(String uri,Object requestBody,Class<T> responseType ){
       return restTemplate.postForObject(uri,requestBody,responseType);
    }

    public <T>T getList(String uri, ParameterizedTypeReference<T> responseType){
        ResponseEntity<T> response=restTemplate
                .exchange(uri, HttpMethod.GET,null,responseType);
        return response.getBody();
    }

    public <T>T get(String uri,Class<T> responseType){
        return restTemplate.getForObject(uri,responseType);
    }

    public void update(String uri,Object requestBody){
         restTemplate.put(uri,requestBody);
    }
    public void delete(String uri) {
        restTemplate.delete(uri);
    }


}
