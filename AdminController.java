package az.developia.demo.Controller;

import az.developia.demo.Entity.AdminEntity;
import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Request.AdminRequest;
import az.developia.demo.Request.ManagerRequest;
import az.developia.demo.Request.UserRequest;
import az.developia.demo.Response.AdminResponse;
import az.developia.demo.Response.ManagerResponse;
import az.developia.demo.Response.UserResponse;
import az.developia.demo.Service.AdminService;
import az.developia.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/adminPanel")

public class AdminController {
    private final AdminService adminService;

    @PostMapping("/login")
    public UserResponse register(@RequestBody UserRequest request){
        return adminService.register(request);
    }




}
