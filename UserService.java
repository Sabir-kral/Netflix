package az.developia.demo.Service;

import az.developia.demo.Entity.AdminEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Repo.AboneRepo;
import az.developia.demo.Repo.RoleRepo;
import az.developia.demo.Repo.UserRepo;
import az.developia.demo.Mapper.UserMapper;
import az.developia.demo.Request.UserRequest;
import az.developia.demo.Response.UserResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo repo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AboneRepo aboneRepo;
    private final LogService logService;


    public UserResponse add(UserRequest request){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(request.getName());
        userEntity.setUsername(request.getUsername());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setEmail(request.getEmail());

        repo.save(userEntity);
        repo.assignPlanBasic(userEntity.getId());
        roleRepo.assignUserRoles(userEntity.getId());
        logService.add("User has added with name: "+userEntity.getName(),"USER_ADDED");
        return UserMapper.toDTO(userEntity);
    }
    public void delete(Long id){
        UserEntity userEntity = repo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        repo.delete(userEntity);
        logService.add("User has deleted with name: "+userEntity.getName(),"USER_DELETED");
    }

    public void isUserExists(String username) {
        if (!repo.findByUsername(username).isPresent()) {
            throw new RuntimeException("User dont exists");
        }
    }

    public UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


    public List<UserResponse> getAll(){
      return   UserMapper.toDTOList(repo.findAll());
    }


    @Transactional // Ensures the database stays consistent
    public UserResponse updateUser(Long id, UserRequest dto) {
        // 1. Find the user or throw a clear error (don't let it fail silently!)
        UserEntity existingUser = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User with ID " + id + " not found"));

        // 2. "Modern" Null-Safe Updates
        // Only update if the frontend actually sent a value
        Optional.ofNullable(dto.getEmail()).ifPresent(existingUser::setEmail);
        Optional.ofNullable(dto.getUsername()).ifPresent(existingUser::setUsername);

        // Handle complex logic (e.g., password hashing) only if provided
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // 3. Save and map back to a Response DTO
        UserEntity savedUser = repo.save(existingUser);
        return new UserResponse(savedUser.getId(),dto.getName(),dto.getUsername(),dto.getPassword(),dto.getEmail());
    }
    public UserEntity findById(Long id){
        UserEntity userEntity = repo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
        return userEntity;
    }
}
