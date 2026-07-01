package com.example.insuranceplatform.mapper;

import com.example.insuranceplatform.entity.User;
import com.example.insuranceplatform.payload.user.UserResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse userToUserResponse(@NonNull User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole()
        );
    }
}
