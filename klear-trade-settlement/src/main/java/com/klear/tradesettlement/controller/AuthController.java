package com.klear.tradesettlement.controller;

import com.klear.tradesettlement.model.AuthenticationRequest;
import com.klear.tradesettlement.service.UserService;
import com.klear.tradesettlement.utils.JwtUtil;
import com.klear.tradesettlement.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/authorize")
    public Mono<String> login(@RequestBody AuthenticationRequest authRequest) {
        return userService.loadUserByUsername(authRequest.getUsername())
                .filter(user ->
                        PasswordUtil.checkPassword(authRequest.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getUsername()))
                .switchIfEmpty(Mono.error(new RuntimeException("Invalid credentials")));
    }

}
