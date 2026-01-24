package com.taxiapp.controller;

import com.taxiapp.service.UserClientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class HomeController {

    private final UserClientService userClientService;

    public HomeController(UserClientService userClientService) {
        this.userClientService = userClientService;
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication, HttpServletRequest request,
                       HttpServletResponse response) {

        String username = authentication.getName();
        model.addAttribute("name", username);

        Integer rideCount = userClientService.getRideCount(username);
        model.addAttribute("rideCount", rideCount);


        String level = userClientService.determineLevel(rideCount);
        model.addAttribute("level", level);

        String cookieName = "lastVisit_" + username;
        String lastVisit = null;

        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (cookieName.equals(c.getName())) {
                    lastVisit = c.getValue();
                    break;
                }
            }
        }

        if (lastVisit != null) {
            LocalDateTime time = LocalDateTime.parse(lastVisit);
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

            model.addAttribute("lastVisitFormatted", time.format(formatter));
        } else {
            model.addAttribute("lastVisitFormatted",
                    "You have no recent visits.");
        }

        Cookie cookie = new Cookie(cookieName, LocalDateTime.now().toString());
        cookie.setMaxAge(60 * 60 * 24 * 7);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "home";
    }


    @GetMapping("/levels")
    public String levels() {
        return "levels";
    }
}
