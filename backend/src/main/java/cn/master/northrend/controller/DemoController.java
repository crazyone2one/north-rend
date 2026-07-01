package cn.master.northrend.controller;

import cn.master.northrend.dto.AuthResponse;
import cn.master.northrend.dto.LoginRequest;
import cn.master.northrend.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 11's papa
 * @since : 2026/6/29, 星期一
 **/
@RestController
@RequiredArgsConstructor
public class DemoController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public AuthResponse demo(@RequestBody LoginRequest loginRequest) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authenticate = authenticationManager.authenticate(authenticationRequest);
        String token = jwtService.generateToken(authenticate);
        // Long expiresAt = jwtService.extractExpirationTime(token);
        Long expiresAt = 10000L;
        return new AuthResponse(token, loginRequest.username(), expiresAt);
    }

    @GetMapping("/api/secured")
    public String secured() {
        return "This is an secured endpoint";
    }

    @GetMapping("/public")
    public String homePage() {
        return "Hello from Spring boot app";
    }
}
