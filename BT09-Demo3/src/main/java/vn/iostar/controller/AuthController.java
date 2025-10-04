package vn.iostar.controller;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import vn.iostar.models.LoginDto;
import vn.iostar.models.SignupDto;
import vn.iostar.entity.Role;
import vn.iostar.entity.Users;
import vn.iostar.repository.RoleRepository;
import vn.iostar.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("User signed-in successfully!");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupDto signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername()))
            return ResponseEntity.badRequest().body("Username is already taken!");
        if (userRepository.existsByEmail(signUpDto.getEmail()))
            return ResponseEntity.badRequest().body("Email is already taken!");

        Users user = new Users();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role role = roleRepository.findByName("USER").orElseThrow();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }
}
