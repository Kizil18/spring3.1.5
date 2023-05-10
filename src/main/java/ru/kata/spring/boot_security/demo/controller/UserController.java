package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.model.User;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleServiceImpl roleService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin")
    public String showAllUser(Model model, Authentication authentication) {
        User user1 = (User)authentication.getPrincipal();
        model.addAttribute("user1", user1);
//        model.addAttribute("allUser", userService.listUser());
//        model.addAttribute("user2", new User());
//        model.addAttribute("allRole", roleService.roleList());
        return "index";
    }

    @GetMapping("/user")
    public String showUser(Model model, Authentication authentication) {
        User user = (User)authentication.getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/addUser")
    public String addNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRole", roleService.roleList());
        return "user-info";
    }
//    @PostMapping("/saveUser")
//    public String saveUser(@ModelAttribute("user2") User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("userId") int id, Model model) {
        model.addAttribute("user3", userService.getUser(id));
        model.addAttribute("allRole", roleService.roleList());
        return "user-update";
    }


//    @PatchMapping("/upUser")
//    public String updateUser(@ModelAttribute("user") User user, @RequestParam("id") int id) {
//        userService.updateUser(id, user);
//        return "redirect:/admin";
//    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }


}
