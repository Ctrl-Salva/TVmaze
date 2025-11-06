package com.example.tvmaze.controllers.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/personagens")
public class AdminPersonagensWebController {
    
    @GetMapping
    public String listarPersonagens(){

        return "admin/personagens/listar";
    }
}
