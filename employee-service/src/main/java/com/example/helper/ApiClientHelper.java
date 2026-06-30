/*
package com.example.helper;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ApiClientHelper {
    private final WebClient webClient;

    public ApiClientHelper(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T>Mono<T> post(String uri,T body,Class<T> responseType){
       return webClient.post()
               .uri(uri)
               .bodyValue(body)
               .retrieve()
               .bodyToMono(responseType);

    }

    public <T> Mono<T> getList(String uri, ParameterizedTypeReference<T> typeReference){
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(typeReference);
    }

    public <T> Mono<T> get(String uri,Class<T> responseType){
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }

    public Mono<String> delete(String uri){
        return webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class);
    }
    public <T>Mono<T> update(String uri,T body,Class<T> responseType ){
        return webClient.put()
                .uri(uri)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }
}
*/
