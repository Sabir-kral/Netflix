package az.developia.demo.Request;

import az.developia.demo.Entity.UserEntity;
import lombok.Data;

@Data
public class AdminRequest {
    private String name;
    private String password;
    private String username;

}
