package management.insight_api.service;

import lombok.RequiredArgsConstructor;
import management.insight_api.dto.request.RegisterUserRequest;
import management.insight_api.dto.response.RegisterUserResponse;
import management.insight_api.model.User;
import management.insight_api.model.enums.UserRole;
import management.insight_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserResponse register(RegisterUserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email já cadastrado");
        }

        User newUser = new User();
        newUser.setName(request.name());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(UserRole.ANALYST);

        userRepository.save(newUser);
        return new RegisterUserResponse(newUser.getName(), newUser.getEmail());
    }
}
