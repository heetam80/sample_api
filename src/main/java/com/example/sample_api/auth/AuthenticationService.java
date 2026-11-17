package com.example.sample_api.auth;

import com.example.sample_api.dto.LoginRequest;
import com.example.sample_api.dto.LoginResponse;
import com.example.sample_api.dto.RegisterRequest;
import com.example.sample_api.exception.LoginArgumentNotValidException;
import com.example.sample_api.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;


@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, AbstractHandlerMethodAdapter abstractHandlerMethodAdapter) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse authentication(LoginRequest loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
            throw new LoginArgumentNotValidException("username is empty");
        }
        if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            throw new LoginArgumentNotValidException("password is empty");
        }

        // 認証
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // JWTトークンを生成
        String token = jwtService.generateToken(loginRequest.getUsername());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        return loginResponse;
    }

    public LoginResponse register(RegisterRequest registerRequest) {

//        バリデーション
        if(registerRequest.getUsername() == null || registerRequest.getUsername().isEmpty()) {
            throw new LoginArgumentNotValidException("username is empty");
        }
        if(registerRequest.getPassword() == null || registerRequest.getPassword().isEmpty()) {
            throw new LoginArgumentNotValidException("password is empty");
        }
        if (userRepository.selectUserByUsername(registerRequest.getUsername()) != null){
            throw new LoginArgumentNotValidException("username already exists");
        }



        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        userRepository.insertUser(registerRequest.getUsername(), encodedPassword);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtService.generateToken(registerRequest.getUsername()));
        return loginResponse;
    }

}