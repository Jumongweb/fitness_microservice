package com.jumongweb.fitness.activityservice.mapper;

import com.jumongweb.fitness.activityservice.dto.ActivityRequest;
import com.jumongweb.fitness.activityservice.dto.ActivityResponse;
import com.jumongweb.fitness.activityservice.model.Activity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    List<ActivityResponse> toActivityResponses(List<Activity> activities);

    ActivityResponse toActivityResponse(Activity savedActivity);

    Activity toActivity(ActivityRequest activityRequest);
}
