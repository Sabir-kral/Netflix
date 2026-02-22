package az.developia.demo.Mapper;

import az.developia.demo.Entity.AdminEntity;
import az.developia.demo.Entity.ManagerEntity;
import az.developia.demo.Response.AdminResponse;
import az.developia.demo.Response.ManagerResponse;

public class ManagerMapper {
    public static ManagerResponse toDTO(ManagerEntity entity){
        ManagerResponse managerResponse = new ManagerResponse();
        managerResponse.setId(entity.getId());
        managerResponse.setName(entity.getName());
        managerResponse.setPassword(entity.getPassword());


        return managerResponse;
    }
}
