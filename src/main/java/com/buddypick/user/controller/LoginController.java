package com.buddypick.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/admin")
    public String accessTest1(){
        return "admin 페이지 ";
    }
    @GetMapping("/user")
    public String accessTest2(){
        return "user 페이지";
    }

}
