package com.seong.accountBook.controller;

import com.seong.accountBook.entity.AccountBook;
import com.seong.accountBook.entity.dto.AccountBookDTO;
import com.seong.accountBook.security.JwtTokenProvider;
import com.seong.accountBook.service.AccountBookService;
import com.seong.accountBook.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/ac")
@RequiredArgsConstructor
public class AccountBookController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AccountBookService accountBookService;
    private final CustomerService customerService;
    
    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createAccountBook(@RequestBody AccountBook accountBook, HttpServletRequest request) {
        Map<String, String> responseBody = new HashMap<>();

        if(!accountBookService.createValidation(accountBook)){
            responseBody.put("message", "필수값 항목이 빠졌습니다.");
        }else{
            String token = jwtTokenProvider.resolveToken(request);
            String email = jwtTokenProvider.getEmailFromJwtToken(token);
            accountBook.setUsrId(customerService.findByEmail(email).get().getId());

            accountBookService.createAccountBook(accountBook);
            responseBody.put("message", "등록 완료되었습니다.");

        }
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateAccountBook(@RequestBody AccountBookDTO acDTO) {
        Map<String, String> responseBody = new HashMap<>();

        System.out.println("data: " + acDTO.toString());
        
        if(accountBookService.validate(acDTO, "update")){
            responseBody.put("message", "수정 완료되었습니다.");
            accountBookService.updateAccountBook(acDTO);
        }else{
            responseBody.put("message", "필수 파라미터가 빠졌습니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteAccountBook(@RequestParam Integer id) {
        Map<String, String> responseBody = new HashMap<>();
        accountBookService.deleteAccountBook(id);
        responseBody.put("message", "삭제가 완료되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/recover")
    public ResponseEntity<Map<String, String>> recoverAccountBook(@RequestParam Integer id) {
        Map<String, String> responseBody = new HashMap<>();
        accountBookService.recoverAccountBook(id);
        responseBody.put("message", "복구가 완료되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getAllAccountBook(@RequestParam Integer userId) {
        Map<String, Object> responseBody;
        responseBody = accountBookService.getAllAccountBook(userId);
        responseBody.put("message", "리스트 load 완료되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    }

    @GetMapping("/list/detail")
    public ResponseEntity<Map<String, Object>> getAccountBookById(@RequestParam Integer id) {
        Map<String, Object> responseBody;
        responseBody = accountBookService.getAccountBookById(id);
        responseBody.put("message", "상세정보 load 완료되었습니다.");

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);

    }
}

