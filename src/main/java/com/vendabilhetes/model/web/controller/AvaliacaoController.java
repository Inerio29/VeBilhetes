package com.vendabilhetes.model.web.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vendabilhetes.model.Avaliacao;
import com.vendabilhetes.model.service.AvaliacaoService;
import com.vendabilhetes.model.service.EventoService;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;
    
    @Autowired
    private EventoService eventoService;


    @GetMapping
    public List<Avaliacao> listarTodos() {
        return avaliacaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Avaliacao> buscarPorId(@PathVariable Integer id) {
        return avaliacaoService.buscarPorId(id);
    }

    @PostMapping
    public Avaliacao salvar(@RequestBody Avaliacao avaliacao) {
        return avaliacaoService.salvar(avaliacao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        avaliacaoService.deletar(id);
    }
    
    @GetMapping("/avaliar")
    public String avaliarEvento(@RequestParam Iterable<Integer> eventoId, Model model) {
        model.addAttribute("evento", eventoService.buscarPorId(eventoId));
        model.addAttribute("avaliacao", new Avaliacao());
        return "avaliarE";
    }

    @PostMapping("/avaliacoes/enviar")
    public String enviarAvaliacao(@ModelAttribute Avaliacao avaliacao) {
        avaliacaoService.salvar(avaliacao);
        return "redirect:/eventos";
    }

    
}
