package com.lardi.phonebook.web;

import com.lardi.phonebook.service.AuthenticationHelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Nikolay Yashchenko
 */
@Controller
public class PagesController {

    @Autowired
    private AuthenticationHelperService authenticationHelperService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHome() {
        return "home";
    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS')")
    @RequestMapping(value = "/authorization", method = RequestMethod.GET)
    public String getRegistration() {
        return "authorization";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/phonebook", method = RequestMethod.GET)
    public String getPhoneBook(Model model) {
        Long currentUserId = authenticationHelperService.getLoggedInUserId();
        model.addAttribute("userId", currentUserId);
        return "phonebook";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/phonebook/edit", method = RequestMethod.GET)
    public String getPhoneBookEdit(Model model) {
        Long currentUserId = authenticationHelperService.getLoggedInUserId();
        model.addAttribute("userId", currentUserId);
        return "phonebook-edit";
    }
}
