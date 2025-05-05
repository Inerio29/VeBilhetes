package com.vendabilhetes.model.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vendabilhetes.model.Usuario;
import com.vendabilhetes.model.Usuario.TipoUsuario;
import com.vendabilhetes.model.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminLoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/alogin")
    public String exibirTelaLoginAdmin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "alogin";
    }

    @PostMapping("/login-admin")
    public String processarLoginAdmin(
            @RequestParam String email,
            @RequestParam String senha,
            Model model,
            HttpSession session) {

        Usuario usuario = usuarioService.autenticar(email, senha);

        if (usuario != null && usuario.getTipoUsuario() == TipoUsuario.ADMIN) {
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/dashAD";
        }

        model.addAttribute("erro", "Credenciais inválidas ou não é administrador");
        model.addAttribute("usuario", new Usuario());
        return "alogin";
    }
    
    
}
