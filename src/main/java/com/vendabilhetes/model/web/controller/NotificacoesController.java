package com.vendabilhetes.model.web.controller;


import com.vendabilhetes.model.Notificacoes;
import com.vendabilhetes.model.service.NotificacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notificacoes")
public class NotificacoesController {

    @Autowired
    private NotificacoesService notificacoesService;

    @GetMapping
    public List<Notificacoes> listarTodos() {
        return notificacoesService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Notificacoes> buscarPorId(@PathVariable Integer id) {
        return notificacoesService.buscarPorId(id);
    }

    @PostMapping
    public Notificacoes salvar(@RequestBody Notificacoes notificacao) {
        return notificacoesService.salvar(notificacao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        notificacoesService.deletar(id);
    }
}
