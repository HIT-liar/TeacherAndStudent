package com.example.demo2.trush;


import com.example.demo2.database.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository  extends CrudRepository<Account, Long> {
    List<Account> findAccountById(int id);
}