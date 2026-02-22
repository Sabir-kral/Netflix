package az.developia.demo.Controller;

import az.developia.demo.Entity.UserEntity;

import az.developia.demo.Repo.UserRepo;
import az.developia.demo.Request.LoginRequest;
import az.developia.demo.Response.LoginResponse;
import az.developia.demo.Service.CustomUserDetailsService;
import az.developia.demo.Service.LogService;
import az.developia.demo.Utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserRepo userRepo;
    private final LogService logService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
        }catch (BadCredentialsException e) {
            throw new RuntimeException("Daxil edilen melumatlar yanlisdir");
        }

        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        UserEntity users = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        logService.add("User logined with username"+request.getUsername(),"USER_LOGINED");
        return new LoginResponse(
                request.getUsername(),
                accessToken,
                refreshToken,
                users.getRoles().toString(),
                users.getId()
        );

    }
}
