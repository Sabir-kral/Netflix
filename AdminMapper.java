package az.developia.demo.Mapper;

import az.developia.demo.Entity.AdminEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Response.AdminResponse;
import az.developia.demo.Response.UserResponse;

public class AdminMapper {
       public static AdminResponse toDTO(AdminEntity entity){
        AdminResponse adminResponse = new AdminResponse();
           adminResponse.setId(entity.getId());
           adminResponse.setName(entity.getName());
           adminResponse.setPassword(entity.getPassword());
           adminResponse.setUsername(entity.getUsername());


        return adminResponse;
    }
}
