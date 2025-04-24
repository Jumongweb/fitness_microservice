package com.jumongweb.fitness.activityservice;

import com.jumongweb.fitness.activityservice.exception.ActivityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId){
        try{
            return Boolean.TRUE.equals(userServiceWebClient.get()
                    .uri("/api/v1/user/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        } catch (WebClientResponseException exception){
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND){
                throw new ActivityException("User not found: " + userId);
            } else if (exception.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new ActivityException(exception.getResponseBodyAsString());
            }
        }
        return false;
    }
}
