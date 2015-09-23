package com.imdb.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    @RequestMapping(value = "/login")
    public String doLogin(){
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String doLogout(){
        return "logout";
    }
}
