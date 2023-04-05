package com.seong.accountBook.service;

import com.seong.accountBook.entity.AccountBook;
import com.seong.accountBook.entity.dto.AccountBookDTO;
import com.seong.accountBook.repository.AccountBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;

    public void createAccountBook(AccountBook accountBook) {

        accountBook.setRegDt(LocalDateTime.now());
        accountBook.setUsedYn("Y");

        accountBookRepository.save(accountBook);
    }

    public void updateAccountBook(AccountBookDTO AccountBookDTO) {
        AccountBook accountBook = accountBookRepository.findById(AccountBookDTO.getId())
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 가계부입니다."));

        accountBook.setSpentMoney(AccountBookDTO.getSpentMoney());
        accountBook.setMemo(AccountBookDTO.getMemo());
        accountBookRepository.save(accountBook);
    }

    public void deleteAccountBook(Integer id) {
        AccountBook accountBook = accountBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 가계부입니다."));

        accountBook.setUsedYn("N");
        accountBook.setUptDt(LocalDateTime.now());

        accountBookRepository.save(accountBook);
    }

    public void recoverAccountBook(Integer id) {
        AccountBook accountBook = accountBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 가계부입니다."));

        accountBook.setUsedYn("Y");
        accountBook.setUptDt(LocalDateTime.now());

        accountBookRepository.save(accountBook);
    }

    public Map<String, Object> getAllAccountBook(Integer usrId) {
        Map<String, Object> result = new HashMap<>();
        List<AccountBook> acList = accountBookRepository.findByUsedYnAndUsrId("Y", usrId);

        List<AccountBookDTO> accountBookList = acList.stream()
                .map(ac -> new AccountBookDTO(ac.getId(), ac.getUsrId(), ac.getDate(), ac.getSpentMoney(),
                        ac.getMemo(), ac.getUsedYn(), ac.getRegDt(), ac.getUptDt()))
                .collect(Collectors.toList());

        result.put("items", accountBookList);
        return result;
    }

    public Map<String, Object> getAccountBookById(Integer id) {
        AccountBook accountBook = accountBookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 아이디를 찾을 수 없습니다."));

        Map<String, Object> result = new HashMap<>();
        result.put("item", accountBook);

        return result;
    }

    public boolean createValidation(AccountBook accountBook){
        String date = String.valueOf(accountBook.getDate());
        String memo = String.valueOf(accountBook.getMemo());
        String money = String.valueOf(accountBook.getSpentMoney());

        if(date.equals("null") || memo.equals("null") || money.equals("null")){
            return false;
        } else {
            return true;
        }
    }

    public boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public boolean validate(AccountBookDTO accountBookDTO, String type) {
        switch(type) {
            case "create":
                if (isNullOrEmpty(accountBookDTO.getMemo()) || isNullOrEmpty(accountBookDTO.getUsedYn())) {
                    return false;
                }
                if (accountBookDTO.getSpentMoney() == null || accountBookDTO.getSpentMoney() <= 0) {
                    return false;
                }
                break;
            case "update":
                if (accountBookDTO.getId() == null || isNullOrEmpty(accountBookDTO.getMemo()) || isNullOrEmpty(accountBookDTO.getUsedYn())) {
                    return false;
                }
                if (accountBookDTO.getSpentMoney() != null && accountBookDTO.getSpentMoney() <= 0) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

}
