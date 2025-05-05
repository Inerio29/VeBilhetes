package com.vendabilhetes.model.web.controller;


import com.vendabilhetes.model.Pagamento;
import com.vendabilhetes.model.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public List<Pagamento> listarTodos() {
        return pagamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Pagamento> buscarPorId(@PathVariable Integer id) {
        return pagamentoService.buscarPorId(id);
    }

    @PostMapping
    public Pagamento salvar(@RequestBody Pagamento pagamento) {
        return pagamentoService.salvar(pagamento);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        pagamentoService.deletar(id);
    }
}
