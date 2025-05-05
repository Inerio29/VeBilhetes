package com.vendabilhetes.model.service;

import com.vendabilhetes.model.Administracao;
import com.vendabilhetes.model.repository.AdministracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdministracaoService {

    @Autowired
    private AdministracaoRepository administracaoRepository;

    public List<Administracao> listarTodos() {
        return administracaoRepository.findAll();
    }

    public Optional<Administracao> buscarPorId(Integer id) {
        return administracaoRepository.findById(id);
    }

    public Administracao salvar(Administracao administracao) {
        return administracaoRepository.save(administracao);
    }

    public void deletar(Integer id) {
        administracaoRepository.deleteById(id);
    }
    
    public Administracao obter() {
        // Exemplo: retorna sempre a administração com id 1
        return administracaoRepository.findById(1)
            .orElseThrow(() -> new RuntimeException("Administração não encontrada"));
    }
}
