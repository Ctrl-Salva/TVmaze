package com.example.tvmaze.controllers.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/pessoas")
public class AdminPessoaWebController {
    
    @GetMapping
    public String listarPessoas(){

        return "admin/pessoas/listar";
    }
}
