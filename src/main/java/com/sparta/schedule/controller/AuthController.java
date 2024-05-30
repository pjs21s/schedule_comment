package com.sparta.schedule.controller;

import com.sparta.schedule.dto.AuthRequest;
import com.sparta.schedule.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/authenticate")
    public String createToken(@RequestBody AuthRequest authRequest) throws Exception {
        if ("user".equals(authRequest.getUsername()) && "password".equals(authRequest.getPassword())) {
            return JwtTokenProvider.generateToken(authRequest.getUsername());
        } else {
            throw new Exception("유효하지 않은 자격증명");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestHeader("Authorization") String token) {
        String username = JwtTokenProvider.extractUsername(token.substring(7));
        if (JwtTokenProvider.validateToken(token.substring(7), username)) {
            return "유효한 토큰";
        } else {
            return "유효하지 않은 토큰";
        }
    }

}