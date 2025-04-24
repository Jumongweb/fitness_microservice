package com.jumongweb.fitness.aiservice.service;

import com.jumongweb.fitness.aiservice.exception.RecommendationNotFound;
import com.jumongweb.fitness.aiservice.model.Recommendation;
import com.jumongweb.fitness.aiservice.repository.RecommendationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> new RecommendationNotFound("Activity not found"));
    }
}
