package br.com.notesrelease.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RouteController {


    @RequestMapping("/")
    public String homeProd(HttpServletRequest requestHandler) {
        requestHandler.getRequestURI();
        return "index.html";
    }


}
