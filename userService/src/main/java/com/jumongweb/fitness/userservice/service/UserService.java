package com.jumongweb.fitness.userservice.service;

import com.jumongweb.fitness.userservice.dto.RegisterRequest;
import com.jumongweb.fitness.userservice.dto.UserResponse;
import com.jumongweb.fitness.userservice.exception.FitnessException;
import com.jumongweb.fitness.userservice.exception.UserNotFoundException;
import com.jumongweb.fitness.userservice.mapper.UserMapper;
import com.jumongweb.fitness.userservice.dto.PostResponse;
import com.jumongweb.fitness.userservice.model.User;
import com.jumongweb.fitness.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public UserResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new FitnessException("Email already exist");
        }
        User user = userMapper.registerRequestToUser(registerRequest);
        User savedUser = userRepository.save(user);

        String fitnessTip = "Welcome to Fitness!";
        try {
            String apiUrl = "https://jsonplaceholder.typicode.com/posts/1";
            PostResponse post = restTemplate.getForObject(apiUrl, PostResponse.class);
            if (post != null) {
                fitnessTip = post.getTitle();
            }
        } catch (FitnessException e) {
            System.err.println("Failed to fetch fitness tip: " + e.getMessage());
        }

        UserResponse userResponse = userMapper.mapUserToUserResponse(savedUser);
        userResponse.setFitnessTip(fitnessTip);
        return userResponse;
    }

    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.mapUserToUserResponse(user);
    }

    public Boolean existByUserId(String userId) {
        return userRepository.existsById(userId);
    }

}
