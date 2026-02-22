package az.developia.demo.Response;

import az.developia.demo.Entity.UserEntity;
import lombok.Data;

@Data
public class AdminResponse {
    private Long id;
    private String name;
    private String password;
    private String username;
}
