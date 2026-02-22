package az.developia.demo.Response;

import az.developia.demo.Entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String username;
    private String accessToken;
    private String refreshToken;
    private String role;
    private Long id;
}