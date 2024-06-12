package com.example.bankingapplication.Controller;


import com.example.bankingapplication.Entity.User;
import com.example.bankingapplication.Service.BankingService;
import com.example.bankingapplication.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BankingService bankingService;

    @GetMapping("/register")
    public String showRegisterForm(Model model)
    {
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user)
    {
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model)
    {
        model.addAttribute("user",new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, HttpSession session,Model model)
    {
        Optional<User> loggedInUser = userService.findByUsername(user.getUsername());
        if(loggedInUser.isPresent() && loggedInUser.get().getPassword().equals(user.getPassword()))
        {
            session.setAttribute("user",loggedInUser.get());
            return "redirect:/home";
        }
        model.addAttribute("user","username or password is invalid");
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String showHome(HttpSession session,Model model)
    {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "home";
    }

    @PostMapping("/deposit")
    public String deposit(HttpSession session, @RequestParam double amount) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            bankingService.deposit(user.getUsername(),amount);
        }
        return "redirect:/home";
    }

    @PostMapping("/withdraw")
    public String withdraw(HttpSession session, @RequestParam double amount) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            bankingService.withdraw(user.getUsername(), amount);
        }
        return "redirect:/home";
    }

    @PostMapping("/transfer")
    public String transfer(HttpSession session, @RequestParam String receiver, @RequestParam double amount) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            bankingService.transfer(user.getUsername(), receiver, amount);
        }
        return "redirect:/home";
    }
    @GetMapping("/logout")
    public String logOut(HttpSession session)
    {
        session.invalidate();
        return "redirect:/login";
    }

}
