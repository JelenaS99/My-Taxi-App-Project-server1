package com.taxiapp.controller;

import com.taxiapp.model.dto.RideDto;
import com.taxiapp.service.UserClientService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class HistoryController {

    private final UserClientService userClientService;

    public HistoryController(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @GetMapping("/history")
    public String history(Model model, Authentication authentication) {

        String username = authentication.getName();

        List<RideDto> rides = userClientService.getRides(username);
        List<String> users = userClientService.getAllUsernames();

        model.addAttribute("rides", rides);
        model.addAttribute("users", users);


        return "history";
    }


    @PostMapping("/delete/{id}")
    public String deleteRide(@PathVariable Long id, RedirectAttributes redirectAttributes) {

        userClientService.deleteRide(id);

        return "redirect:/history";
    }


    @PostMapping("/delete/user/{username}")
    public String deleteUser(@PathVariable String username, RedirectAttributes redirectAttributes) {

        userClientService.deleteUserByUsername(username);

        return "redirect:/history";
    }


}
