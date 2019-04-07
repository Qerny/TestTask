package com.student.testtask.controllers;

import com.student.testtask.repositories.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    UserRepositoryImpl userRepository;

    @GetMapping("/")
    public ModelAndView index() {
        Map<String, String> model = new HashMap<>();
        model.put("user1Login", userRepository.getUserById("1").getLogin());
        model.put("user2Login", userRepository.getUserById("2").getLogin());
        return new ModelAndView("index", model);
    }
}
