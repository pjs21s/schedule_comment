package com.sparta.schedule.controller;

import com.sparta.schedule.dto.AuthRequest;
import com.sparta.schedule.dto.AuthResponse;
import com.sparta.schedule.dto.RegisterRequest;
import com.sparta.schedule.dto.RegisterResponse;
import com.sparta.schedule.model.User;
import com.sparta.schedule.service.UserService;
import com.sparta.schedule.util.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

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

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        try {
            userService.register(registerRequest);
            RegisterResponse registerResponse = new RegisterResponse("유저 등록 성공");
            return new ResponseEntity<>(registerResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new RegisterResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            User user = userService.login(authRequest.getUsername(), authRequest.getPassword());
            String token = JwtTokenProvider.generateToken(user.getUsername());
            AuthResponse authResponse = new AuthResponse("로그인 성공", token);
            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthResponse("유효하지 않은 사용자 이름 혹은 비밀번호", null), HttpStatus.UNAUTHORIZED);
        }
    }

}