package com.jumongweb.fitness.userservice.mapper;

import com.jumongweb.fitness.userservice.dto.RegisterRequest;
import com.jumongweb.fitness.userservice.dto.UserResponse;
import com.jumongweb.fitness.userservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User registerRequestToUser(RegisterRequest registerRequest);

    UserResponse mapUserToUserResponse(User savedUser);

}
