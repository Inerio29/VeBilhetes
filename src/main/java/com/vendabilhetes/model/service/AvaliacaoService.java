package com.vendabilhetes.model.service;


import com.vendabilhetes.model.Avaliacao;
import com.vendabilhetes.model.repository.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    public List<Avaliacao> listarTodos() {
        return avaliacaoRepository.findAll();
    }

    public Optional<Avaliacao> buscarPorId(Integer id) {
        return avaliacaoRepository.findById(id);
    }

    public List<Avaliacao> buscarPorEvento(Integer eventoId) {
        return avaliacaoRepository.findByEventoIdEvento(eventoId);
    }

    public Avaliacao salvar(Avaliacao avaliacao) {
        return avaliacaoRepository.save(avaliacao);
    }

    public void deletar(Integer id) {
        avaliacaoRepository.deleteById(id);
    }
}
