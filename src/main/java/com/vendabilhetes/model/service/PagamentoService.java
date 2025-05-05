package com.vendabilhetes.model.service;


import com.vendabilhetes.model.Pagamento;
import com.vendabilhetes.model.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

    public Optional<Pagamento> buscarPorId(Integer id) {
        return pagamentoRepository.findById(id);
    }

    public List<Pagamento> buscarPorUsuario(Integer usuarioId) {
        return pagamentoRepository.findByUsuarioIdUsuario(usuarioId);
    }

    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public void deletar(Integer id) {
        pagamentoRepository.deleteById(id);
    }
}
