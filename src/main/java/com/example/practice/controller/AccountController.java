package com.example.practice.controller;

import com.example.practice.dto.AccountDTO;
import com.example.practice.dto.RegisterDTO;
import com.example.practice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping(value = "/register")
    public String registerForm(Model model) {
        return "account/registerForm.jsp";
    }
    @PostMapping(value = "/register/new")
    public String register(Model model, RegisterDTO registerDTO){
        String result = accountService.register(registerDTO);
        System.out.println("register: "+result);
        if(result.equals("success")){
            return "redirect:/";
        }else{
            model.addAttribute("error", result);
            return "account/registerForm.jsp";
        }
    }
    @GetMapping(value = "/login")
    public String loginForm(Model model) {
        return "account/loginForm.jsp";
    }
    @PostMapping(value = "/login/account")
    public String login(Model model, AccountDTO accountDTO, HttpServletResponse response) {
        String result = accountService.login(accountDTO);
        if(result.equals("success")){
            System.out.println("성공");
            Cookie idCookie = new Cookie("accountId",String.valueOf(accountDTO.getUsername()));
            idCookie.setPath("/");
            response.addCookie(idCookie);
            return "redirect:/";
        }else{
            model.addAttribute("error", result);
            return "account/loginForm.jsp";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletResponse response, Model model){
        Cookie cookie = new Cookie("accountId",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}


