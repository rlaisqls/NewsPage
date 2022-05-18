package com.example.practice.service;

import com.example.practice.dto.AccountDTO;
import com.example.practice.dto.RegisterDTO;
import com.example.practice.entity.Account;
import com.example.practice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountRepository accountRepository;
    public String register(RegisterDTO newAccount) {
        String username = newAccount.getUsername();
        String password = newAccount.getPassword();
        String passwordcheck = newAccount.getPasswordcheck();
        if(username.equals("") || password.equals("") || passwordcheck.equals("")) return "모두 입력하세요<br>";
        if(!password.equals(passwordcheck)) return "비밀번호를 다시 확인하세요<br>";

        Account optionalrepository = accountRepository.findByUsername(username);
        if(optionalrepository!=null) return "이미 존재하는 아이디입니다<br>";

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        return "success";
    }
    public String login(AccountDTO accountDTO){
        String username = accountDTO.getUsername();
        String password = accountDTO.getPassword();
        if(username.equals("") || password.equals("")) return "모두 입력하세요<br>";
        Account optionalrepository = accountRepository.findByUsername(username);
        if(optionalrepository==null) return "존재하지 않는 아이디입니다<br>";
        if(!optionalrepository.getPassword().equals(password)) return "비밀번호가 틀렸습니다<br>";
        return "success";
    }

}




