package com.taskmasterAWSJava.Java401.taskmaster.LukeAWS;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class StaticController {

    @GetMapping("/addtask")
    public String getAddTask() {
        return "addtask";
    }

    @GetMapping("/")
    public String getHome() {
        return "home";
    }
}
