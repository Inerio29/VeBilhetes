package com.vendabilhetes.model.web.controller;


import com.vendabilhetes.model.Administracao;
import com.vendabilhetes.model.service.AdministracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/administracao")
public class AdministracaoController {

    @Autowired
    private AdministracaoService administracaoService;

    @GetMapping
    public List<Administracao> listarTodos() {
        return administracaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Administracao> buscarPorId(@PathVariable Integer id) {
        return administracaoService.buscarPorId(id);
    }

    @PostMapping
    public Administracao salvar(@RequestBody Administracao administracao) {
        return administracaoService.salvar(administracao);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        administracaoService.deletar(id);
    }
}
