package com.taxiapp.controller;

import com.taxiapp.model.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {


    @GetMapping("/login")
    public String getLoginPage(Model model) {

        model.addAttribute("loginDto", new LoginDto());

        return "login";
    }


}
