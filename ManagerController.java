package az.developia.demo.Controller;

import az.developia.demo.Entity.ManagerEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Entity.VideoEntity;
import az.developia.demo.Mapper.UserMapper;
import az.developia.demo.Request.ManagerRequest;
import az.developia.demo.Request.UserRequest;
import az.developia.demo.Response.ManagerResponse;
import az.developia.demo.Response.UserResponse;
import az.developia.demo.Service.ManagerService;
import az.developia.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
@CrossOrigin(origins = "*")
public class ManagerController {
    private final ManagerService managerService;
    @PostMapping("/addManager")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserResponse addManager(@RequestBody UserRequest request){
        return managerService.addManager(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String UpdateManager(@PathVariable Long id,@RequestBody UserRequest request){
        managerService.redakteManager(id,request);
        return "redakte edildi";
    }
    @DeleteMapping("/deleteManager/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void deleteManager(@PathVariable Long id){
        managerService.deleteManager(id);
    }
    @GetMapping("/findManager")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Optional<UserEntity> find(@RequestParam String username){
        return managerService.findManagerByName(username);
    }

}
