package com.taxiapp.controller;

import com.taxiapp.service.UserClientService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    private final UserClientService userClientService;

    public HomeController(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {

        String username = authentication.getName();
        model.addAttribute("name", username);

        Integer rideCount = userClientService.getRideCount(username);
        model.addAttribute("rideCount", rideCount);


        String level = userClientService.determineLevel(rideCount);
        model.addAttribute("level", level);

        return "home";
    }


    @GetMapping("/levels")
    public String levels() {
        return "levels";
    }
}
