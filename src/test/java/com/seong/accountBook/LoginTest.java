package com.seong.accountBook;

import com.seong.accountBook.entity.dto.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLoginSuccess() throws Exception {
        // 로그인에 필요한 데이터
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("test@naver.com");
        customerDTO.setPassword("test");

        // 로그인 요청
        ResponseEntity<String> response = restTemplate.postForEntity("/login", customerDTO, String.class);

        // 상태코드 200 확인
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // 반환값이 "로그인 성공했습니다." 인지 확인
        assertEquals("로그인 성공했습니다.", response.getBody());
    }

    @Test
    public void testLoginFail() throws Exception {
        // 로그인에 필요한 데이터
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setEmail("test@test.com");
        customerDTO.setPassword("wrong_password");

        // 로그인 요청
        ResponseEntity<String> response = restTemplate.postForEntity("/login", customerDTO, String.class);

        // 상태코드 401 확인
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        // 반환값이 "이메일이 맞지않거나 패스워드가 다릅니다." 인지 확인
        assertEquals("이메일이 맞지않거나 패스워드가 다릅니다.", response.getBody());
    }
}
