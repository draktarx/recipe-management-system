package recipes.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.dto.AuthUserAdapter;
import recipes.dto.AuthUserDTO;
import recipes.entity.AuthUser;
import recipes.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUser registerUser(AuthUserDTO request) {
        var user = new AuthUser();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        AuthUser savedUser = repository.save(user);
        return savedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
    throws UsernameNotFoundException {
        AuthUser user = repository.findByEmail(email)
                                  .orElseThrow(() -> new UsernameNotFoundException("Not found"));

        return new AuthUserAdapter(user);
    }

    public boolean userExistsByEmail(String email) {
        return repository.existsByEmail(email);
    }

}
