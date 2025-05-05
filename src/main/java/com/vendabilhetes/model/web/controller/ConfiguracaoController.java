package com.vendabilhetes.model.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vendabilhetes.model.ConfiguracaoSistema;
import com.vendabilhetes.model.service.ConfiguracaoService;

@Controller
@RequestMapping("/configuracoes")
public class ConfiguracaoController {

    private final ConfiguracaoService configuracaoService;

    public ConfiguracaoController(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("config", configuracaoService.getConfiguracaoAtual());
        return "configS"; // Nome da página HTML (ex: configS.html)
    }

    @PostMapping("/salvar")
    public String salvarConfiguracoes(@ModelAttribute ConfiguracaoSistema config) {
        configuracaoService.salvar(config);
        return "redirect:/configuracoes";
    }
}
