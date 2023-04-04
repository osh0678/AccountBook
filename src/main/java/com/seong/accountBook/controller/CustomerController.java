package com.seong.accountBook.controller;

import com.seong.accountBook.entity.Customer;
import com.seong.accountBook.entity.dto.CustomerDTO;
import com.seong.accountBook.security.JwtTokenProvider;
import com.seong.accountBook.security.SecurityConfig;
import com.seong.accountBook.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomerController  {

    private final CustomerService customerService;
    private final SecurityConfig config;
    private final JwtTokenProvider provider;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody Customer customer) {
        Map<String, String> responseBody = new HashMap<>();

        if (!customerService.existsByEmail(customer.getEmail())){
            String encodedPassword = config.passwordEncoder().encode(customer.getPassword());

            customer.setPassword(encodedPassword);
            customer.setRegDt(LocalDateTime.now());

            customerService.signup(customer);

            responseBody.put("message", "회원가입 완료되었습니다.");

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        }else{
            responseBody.put("message", "중복된 계정입니다.");

            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }

    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody CustomerDTO customerDTO) {
        String email = customerDTO.getEmail();
        String password = customerDTO.getPassword();

        Customer loadedCustomer = customerService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));

        if (config.passwordEncoder().matches(password, loadedCustomer.getPassword())) {
            // 로그인 성공
            // Access Token 발급 및 반환
            String token = provider.createToken(customerDTO.getEmail(), 60 * 60 * 1000);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "로그인 성공했습니다.");
            responseBody.put("access_token", token);

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } else {
            // 로그인 실패
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("message", "이메일이 맞지않거나 패스워드가 다릅니다.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "로그아웃했습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
