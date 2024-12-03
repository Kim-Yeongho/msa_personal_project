package com.example.msa_exam.auth;

import com.example.msa_exam.auth.core.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @Value("${server.port}")
    private String serverPort;

    private final AuthService authService;

    @PostMapping("/auth/sign-in")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody SignInRequest signInRequest) {
        addCustomHeader("Server-Port", serverPort);
        String token = authService.signIn(signInRequest.getUserId(), signInRequest.getPassword());
        return ResponseEntity.ok().header("Server-Port", serverPort).body(token);
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        addCustomHeader("Server-Port", serverPort);
        User createdUser = authService.signUp(user);
        return ResponseEntity.ok().header("Server-Port", serverPort).body(createdUser);
    }

//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    static class AuthResponse {
//        private String access_token;
//    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    static class SignInRequest {
        private Long userId;
        private String password;
    }

    private void addCustomHeader(String headerName, String headerValue){
        HttpHeaders headers = new HttpHeaders();
        headers.set(headerName, headerValue);
    }

}
