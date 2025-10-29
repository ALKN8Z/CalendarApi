package org.example.calendarapi.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;


@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal == null) {
            return "redirect:/";
        }

        Map<String, Object> claims = principal.getClaims();
        model.addAttribute("username", claims.get("preferred_username"));
        model.addAttribute("family_name", claims.get("family_name"));
        model.addAttribute("email", claims.get("email"));
        model.addAttribute("email_verified", claims.get("email_verified"));
        model.addAttribute("exp", claims.get("exp"));

        return "profile";
    }
}
