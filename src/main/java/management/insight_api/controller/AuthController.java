package management.insight_api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import management.insight_api.config.JwtUtil;
import management.insight_api.dto.request.LoginRequest;
import management.insight_api.dto.request.RegisterUserRequest;
import management.insight_api.dto.response.LoginResponse;
import management.insight_api.dto.response.RegisterUserResponse;
import management.insight_api.model.User;
import management.insight_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        User user = (User) authentication.getPrincipal();
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new LoginResponse(token));
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse>register(@Valid @RequestBody RegisterUserRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }
}
