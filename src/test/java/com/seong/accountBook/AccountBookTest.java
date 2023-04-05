package com.seong.accountBook;

import com.seong.accountBook.entity.AccountBook;
import com.seong.accountBook.entity.dto.AccountBookDTO;
import com.seong.accountBook.repository.AccountBookRepository;
import com.seong.accountBook.service.AccountBookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountBookTest {
    @Mock
    private AccountBookRepository accountBookRepository;

    @InjectMocks
    private AccountBookService accountBookService;

    @Test
    @DisplayName("가계부 생성 테스트")
    void createAccountBookTest() {
        /** Given
         *  가계부(AccountBook) 객체를 생성하고 데이터를 입력
         */
        AccountBook accountBook = new AccountBook();
        accountBook.setDate(LocalDate.now());
        accountBook.setMemo("식비");
        accountBook.setSpentMoney(50000);
        accountBook.setUsrId(1);

        /** When
         * accountBookService.createAccountBook(accountBook)를 실행
         * 새로운 가계부를 데이터베이스에 저장
         */
        accountBookService.createAccountBook(accountBook);

        /** Then
         * accountBookRepository.save(accountBook)가 1번 호출되었는지 검증
         * 새로운 가계부가 데이터베이스에 저장되었는지 확인
         */
        verify(accountBookRepository, times(1)).save(accountBook);
    }

    @Test
    @DisplayName("가계부 수정 테스트")
    void updateAccountBookTest() {
        /** Given
         * 테스트 데이터와 해당 메서드에 필요한 객체를 생성
         * 해당 메서드 내에서 사용되는 객체는 mock 객체로 대체
         * mock 객체로 대체하는 객체는 @Mock 어노테이션을 이용하여 생성
         * 이후 해당 mock 객체를 이용하여 메서드의 동작에 대한 기대 값을 설정
         */
        Integer accountBookId = 1;
        AccountBookDTO accountBookDTO = new AccountBookDTO(1, 1, LocalDate.now(), 50000, "식비", "Y", LocalDateTime.now(), null );

        accountBookDTO.setId(accountBookId);
        accountBookDTO.setSpentMoney(70000);
        accountBookDTO.setMemo("생활비");

        AccountBook accountBook = new AccountBook();
        accountBook.setId(accountBookId);
        accountBook.setSpentMoney(50000);
        accountBook.setMemo("식비");
        accountBook.setUsrId(1);
        when(accountBookRepository.findById(accountBookId)).thenReturn(Optional.of(accountBook));

        /** When
         * updateAccountBook(accountBookDTO) 담아서 update 호출
         */
        accountBookService.updateAccountBook(accountBookDTO);

        /** Then
         * verify 메소드는 accountBookRepository 객체의 save 메소드가 한 번 호출되었는지 확인
         * ArgumentCaptor 클래스를 사용하여 호출된 AccountBook 객체를 캡처하고, capture 메소드를 사용하여 해당 객체를 가져온다
         * assertEquals 메소드를 사용하여 예상 결과값과 실제 결과값을 비교
         * accountBookDTO.getSpentMoney()와 accountBookDTO.getMemo()는 accountBookDTO 객체의 속성값을 가져와서, argument.getValue().getSpentMoney()와 argument.getValue().getMemo()는 save 메소드 호출 시 전달된 AccountBook 객체의 속성값을 가져와서, 각각 비교
         * save 메소드가 호출될 때 전달된 AccountBook 객체의 spentMoney와 memo 속성값이 예상한 값과 일치하는지 확인
         */
        ArgumentCaptor<AccountBook> argument = ArgumentCaptor.forClass(AccountBook.class);
        verify(accountBookRepository, times(1)).save(argument.capture());
        assertEquals(accountBookDTO.getSpentMoney(), argument.getValue().getSpentMoney());
        assertEquals(accountBookDTO.getMemo(), argument.getValue().getMemo());
    }

    @Test
    @DisplayName("가계부 삭제 테스트")
    void deleteAccountBookTest() {
        /** Given
         * 테스트 실행에 필요한 객체 생성 및 초기화
         * accountBookId: 삭제할 AccountBook의 ID 값
         * accountBook: 테스트를 위한 AccountBook 객체 생성
         *
         * when(accountBookRepository.findById(accountBookId)).thenReturn(Optional.of(accountBook)):
         * accountBookId에 해당하는 AccountBook이 존재한다고 가정하고,
         * accountBookRepository.findById() 메소드 호출 시 accountBook을 반환하도록 설정
         */
        Integer accountBookId = 1;
        AccountBook accountBook = new AccountBook();
        accountBook.setId(accountBookId);
        accountBook.setUsedYn("Y");
        accountBook.setUptDt(LocalDateTime.now());

        when(accountBookRepository.findById(accountBookId)).thenReturn(Optional.of(accountBook));

        /** When
         * accountBookId에 해당하는 AccountBook을 삭제하는 메소드를 호출
         */
        accountBookService.deleteAccountBook(accountBookId);

        /** Then
         * accountBookRepository.findById() 메소드가 accountBookId 값으로 정확히 1번 호출되었는지 검증
         * accountBookRepository.save() 메소드가 accountBook 객체로 정확히 1번 호출되었는지 검증
         * assertEquals("N", accountBook.getUsedYn()): 삭제된 AccountBook의 usedYn 속성 값이 "N"인지 검증
         */
        verify(accountBookRepository, times(1)).findById(accountBookId);
        verify(accountBookRepository, times(1)).save(accountBook);
        assertEquals("N", accountBook.getUsedYn());
    }

    @Test
    @DisplayName("가계부 복구 테스트")
    void recoverAccountBookTest() {
        /** Given
         * 삭제된 가계부의 ID값을 가진 AccountBook 객체 생성
         * accountBookRepository의 findById() 메소드가 해당 객체를 리턴하도록 Stubbing
         */

        Integer accountId = 1;
        AccountBook accountBook = new AccountBook();
        accountBook.setId(accountId);
        accountBook.setUsedYn("N");
        accountBook.setUptDt(LocalDateTime.now());

        when(accountBookRepository.findById(accountId)).thenReturn(Optional.of(accountBook));

        /** When
         * recoverAccountBook() 메소드에 삭제된 가계부 ID값을 인자로 전달하여 호출
          */
        accountBookService.recoverAccountBook(accountId);

        /** Then
         * accountBookRepository의 findById() 메소드가 해당 ID값으로 1회 호출되었는지 확인
         * accountBookRepository의 save() 메소드가 해당 객체로 1회 호출되었는지 확인
         * 삭제된 가계부의 UsedYn값이 Y로 변경되었는지 확인
         */
        verify(accountBookRepository, times(1)).findById(accountId);
        verify(accountBookRepository, times(1)).save(accountBook);
        assertEquals("Y", accountBook.getUsedYn());
    }

    @Test
    @DisplayName("특정 사용자와 삭제하지않은 모든 가계부 조회 테스트")
    void getAllAccountBookTest() {
        // Given
        List<AccountBook> accountBookList = new ArrayList<>();
        LocalDateTime fixedDateTime = LocalDateTime.now().minusMinutes(5);

        AccountBook accountBook1 = new AccountBook();
        accountBook1.setId(1);
        accountBook1.setUsrId(1);
        accountBook1.setDate(LocalDate.of(2023, 4, 5));
        accountBook1.setSpentMoney(50000);
        accountBook1.setMemo("식비");
        accountBook1.setUsedYn("Y");
        accountBook1.setRegDt(fixedDateTime);
        accountBook1.setUptDt(null);
        accountBookList.add(accountBook1);

        AccountBook accountBook2 = new AccountBook();
        accountBook2.setId(2);
        accountBook2.setUsrId(1);
        accountBook2.setDate(LocalDate.of(2023, 4, 4));
        accountBook2.setSpentMoney(20000);
        accountBook2.setMemo("교통비");
        accountBook2.setUsedYn("Y");
        accountBook2.setRegDt(fixedDateTime);
        accountBook2.setUptDt(null);
        accountBookList.add(accountBook2);

        given(accountBookRepository.findByUsedYnAndUsrId("Y", 1)).willReturn(accountBookList);

        // When
        Map<String, Object> result = accountBookService.getAllAccountBook(1);

        // Then
        List<AccountBookDTO> accountBookDTOList = (List<AccountBookDTO>) result.get("items");
        assertThat(accountBookDTOList).hasSize(2);

        AccountBookDTO accountBookDTO1 = new AccountBookDTO(1, 1, LocalDate.of(2023, 4, 5), 50000, "식비", "Y", fixedDateTime, null );
        AccountBookDTO accountBookDTO2 = new AccountBookDTO(2, 1,LocalDate.of(2023, 4, 4), 20000, "교통비", "Y", fixedDateTime, null );

        assertThat(accountBookDTOList.get(0)).usingRecursiveComparison().isEqualTo(accountBookDTO1);
        assertThat(accountBookDTOList.get(1)).usingRecursiveComparison().isEqualTo(accountBookDTO2);
    }

    @Test
    @DisplayName("id로 가계부 상세조회 테스트")
    void getAccountBookByIdTest() {
        // Given
        LocalDateTime fixedDateTime = LocalDateTime.now().minusMinutes(5);

        AccountBook accountBook = new AccountBook();
        accountBook.setId(1);
        accountBook.setUsrId(1);
        accountBook.setDate(LocalDate.of(2023, 4, 5));
        accountBook.setSpentMoney(50000);
        accountBook.setMemo("식비");
        accountBook.setUsedYn("Y");
        accountBook.setRegDt(fixedDateTime);
        accountBook.setUptDt(null);

        given(accountBookRepository.findById(1)).willReturn(Optional.of(accountBook));

        // When
        Map<String, Object> result = accountBookService.getAccountBookById(1);
        System.out.println("result : " + result);

        // Then
        assertThat(result.containsKey("item")).isTrue();
        assertThat(result.get("item")).isNotNull();

        AccountBook ac = (AccountBook) result.get("item");
        assertThat(ac.getId()).isEqualTo(1);
        assertThat(ac.getUsrId()).isEqualTo(1);
        assertThat(ac.getDate()).isEqualTo(LocalDate.of(2023, 4, 5));
        assertThat(ac.getSpentMoney()).isEqualTo(50000);
        assertThat(ac.getMemo()).isEqualTo("식비");
        assertThat(ac.getUsedYn()).isEqualTo("Y");
        assertThat(ac.getRegDt()).isEqualTo(fixedDateTime);
        assertThat(ac.getUptDt()).isNull();
    }






}
