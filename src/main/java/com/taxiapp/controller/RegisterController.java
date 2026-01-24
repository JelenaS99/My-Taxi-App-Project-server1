package com.taxiapp.controller;

import com.taxiapp.model.dto.RegisterDto;
import com.taxiapp.service.UserClientService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/register")
public class RegisterController {


    private final UserClientService userClientService;

    public RegisterController(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @GetMapping("/register")
    public String getRegisterPage() {

        return "register";

    }

    @PostMapping("/register")
    public String register(@Valid RegisterDto registerDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerData", registerDto);
            redirectAttributes.addFlashAttribute("nameError", bindingResult.hasFieldErrors("name"));
            redirectAttributes.addFlashAttribute("surnameError", bindingResult.hasFieldErrors("surname"));
            redirectAttributes.addFlashAttribute("usernameError", bindingResult.hasFieldErrors("username"));
            redirectAttributes.addFlashAttribute("emailError", bindingResult.hasFieldErrors("email"));
            redirectAttributes.addFlashAttribute("passwordError", bindingResult.hasFieldErrors("password"));
            redirectAttributes.addFlashAttribute("confirmPasswordError", bindingResult.hasFieldErrors("confirmPassword"));
            return "redirect:/register/register";
        }

        boolean registered = userClientService.registerUser(registerDto);

        if (!registered) {
            return "redirect:/register/register";
        }

        return "redirect:/login";
    }
}
