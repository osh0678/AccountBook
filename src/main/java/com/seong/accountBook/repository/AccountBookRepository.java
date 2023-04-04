package com.seong.accountBook.repository;

import com.seong.accountBook.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountBookRepository extends JpaRepository<AccountBook, Integer> {
    List<AccountBook> findByUsedYnAndUsrId(String usedYn, Integer usrId);
}
