package az.developia.demo.Controller;

import az.developia.demo.Entity.UserEntity;
import az.developia.demo.Request.UserRequest;
import az.developia.demo.Response.UserResponse;
import az.developia.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping("/addUser")
    public UserResponse add(@RequestBody UserRequest request){
        return userService.add(request);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserResponse> findall(){
      return   userService.getAll();
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAuthority('ROLE_GET_USER')")
    public UserEntity getCurrentUser(){
        return userService.getCurrentUser();
    }

    @GetMapping("/isUserExists")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void isUserExists(String username){
        userService.isUserExists(username);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest updateDto) {

        UserResponse updatedUser = userService.updateUser(id, updateDto);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserEntity findById(@PathVariable Long id){
        return userService.findById(id);
    }


}
