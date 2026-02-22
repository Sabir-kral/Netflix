package az.developia.demo.Mapper;

import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toDTO(UserEntity entity){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(entity.getId());
        userResponse.setName(entity.getName());
        userResponse.setUsername(entity.getUsername());
        userResponse.setPassword(entity.getPassword());
        userResponse.setEmail(entity.getEmail());

        return userResponse;
    }
    public static List<UserResponse> toDTOList(List<UserEntity> userEntities){
        return userEntities.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

}
