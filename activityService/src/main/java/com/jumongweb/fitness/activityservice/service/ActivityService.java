package com.jumongweb.fitness.activityservice.service;

import com.jumongweb.fitness.activityservice.UserValidationService;
import com.jumongweb.fitness.activityservice.dto.ActivityRequest;
import com.jumongweb.fitness.activityservice.dto.ActivityResponse;
import com.jumongweb.fitness.activityservice.exception.ActivityException;
import com.jumongweb.fitness.activityservice.exception.UserNotFoundException;
import com.jumongweb.fitness.activityservice.mapper.ActivityMapper;
import com.jumongweb.fitness.activityservice.model.Activity;
import com.jumongweb.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        boolean isValidUser = userValidationService.validateUser(activityRequest.getUserId());
        if (!isValidUser) {
            throw new ActivityException("Invalid user: " + activityRequest.getUserId());
        }
        Activity activity = activityMapper.toActivity(activityRequest);
        Activity savedActivity = activityRepository.save(activity);

        // Publish to RabbitMQ for AI Processing
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
        } catch (ActivityException exception) {
            log.error("Failed to publish activity", exception);
        }

        return activityMapper.toActivityResponse(savedActivity);
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activityMapper.toActivityResponses(activities);
    }

    public ActivityResponse getActivityById(String activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ActivityException("Activity with id: " + activityId + " not found"));
        return activityMapper.toActivityResponse(activity);
    }

}
