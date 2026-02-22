package az.developia.demo.Service;

import az.developia.demo.Entity.ManagerEntity;
import az.developia.demo.Entity.RoleEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Entity.VideoEntity;
import az.developia.demo.Mapper.UserMapper;
import az.developia.demo.Repo.ManagerRepo;
import az.developia.demo.Repo.RoleRepo;
import az.developia.demo.Repo.UserRepo;
import az.developia.demo.Repo.VideoRepo;
import az.developia.demo.Request.UserRequest;
import az.developia.demo.Response.ManagerResponse;
import az.developia.demo.Response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepo managerRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final LogService logService;
    private final VideoRepo videoRepo;

    public UserResponse addManager(UserRequest managerRequest){
        UserEntity managerEntity = new UserEntity();
        managerEntity.setName(managerRequest.getName());
        managerEntity.setUsername(managerRequest.getUsername());
        managerEntity.setEmail(managerRequest.getEmail());
        managerEntity.setPassword(passwordEncoder.encode(managerRequest.getPassword()));

        userRepo.save(managerEntity);
        roleRepo.assignManagerRoles(managerEntity.getId());
        logService.add("Manager has added with name: "+managerEntity.getName(),"MANAGER_ADDED");
        return UserMapper.toDTO(managerEntity);
    }

    public String redakteManager(Long id,UserRequest request){
        UserEntity managerEntity = userRepo.findById(id).orElseThrow(()->new RuntimeException("Manager Not Found"));
        managerEntity.setName(request.getName());
        managerEntity.setPassword(request.getPassword());

        logService.add("Manager has update with name: "+managerEntity.getName(),"MANAGER_UPDATED");
        return "Manager Redakte Edildi";
    }

    public void deleteManager(Long id){
        UserEntity managerEntity = userRepo.findById(id).orElseThrow(()-> new RuntimeException("Manager Not Found"));
        userRepo.delete(managerEntity);
        logService.add("Manager has deleted with name: "+managerEntity.getName(),"MANAGER_DELETED");
    }

    public Optional<UserEntity> findManagerByName(String username){
        return userRepo.findManagerByUsername(username);
    }



}
