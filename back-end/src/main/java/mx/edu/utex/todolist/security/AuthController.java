package mx.edu.utex.todolist.security;

import com.example.RepasoCesar.security.dto.AuthRequest;
import com.example.RepasoCesar.security.dto.AuthResponse;
import com.example.RepasoCesar.user.model.User;
import com.example.RepasoCesar.user.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Usuario o contraseña incorrectos", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        long expirationTime = jwtUtil.getExpirationTime();

        return new AuthResponse(jwt, user.getId(), user.getUsername(), expirationTime);
    }
}
