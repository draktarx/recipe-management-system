package recipes.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.dto.AuthUserDTO;
import recipes.entity.AuthUser;

@Service
public class AuthUserCryptService {

    private final PasswordEncoder passwordEncoder;

    public AuthUserCryptService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUser initNewUser(AuthUserDTO request) {
        var user = new AuthUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return user;
    }

}
