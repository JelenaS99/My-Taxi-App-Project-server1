package com.taxiapp.controller;

import com.taxiapp.model.dto.RideDto;
import com.taxiapp.service.UserClientService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderRidesController {


    private final UserClientService userClientService;

    public OrderRidesController(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @GetMapping("/order-taxi")
    public String orderTaxi(Model model) {
        model.addAttribute("rideDto", new RideDto());
        return "order-taxi";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }

    @PostMapping("/rides")
    public String orderRide(
            @Valid @ModelAttribute("rideDto") RideDto form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "order-taxi";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();


        userClientService.createRide(form, username);

        return "redirect:/success";
    }
}
