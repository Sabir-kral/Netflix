package az.developia.demo.Service;

import az.developia.demo.Entity.AdminEntity;
import az.developia.demo.Entity.ManagerEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Mapper.AdminMapper;
import az.developia.demo.Mapper.ManagerMapper;
import az.developia.demo.Mapper.UserMapper;
import az.developia.demo.Repo.AdminRepo;
import az.developia.demo.Repo.ManagerRepo;
import az.developia.demo.Repo.RoleRepo;
import az.developia.demo.Repo.UserRepo;
import az.developia.demo.Request.AdminRequest;
import az.developia.demo.Request.ManagerRequest;
import az.developia.demo.Request.UserRequest;
import az.developia.demo.Response.AdminResponse;
import az.developia.demo.Response.ManagerResponse;
import az.developia.demo.Response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepo adminRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ManagerRepo managerRepo;
    private final RoleRepo roleRepo;

    public UserResponse register(UserRequest adminRequest){
        UserEntity admin = new UserEntity();
        admin.setName(adminRequest.getName());
        admin.setUsername(adminRequest.getUsername());
        admin.setPassword(passwordEncoder.encode(adminRequest.getPassword()));
        admin.setEmail(adminRequest.getEmail());

        userRepo.save(admin);
        roleRepo.assignAdminRoles(admin.getId());
        return UserMapper.toDTO(admin);
    }




}
