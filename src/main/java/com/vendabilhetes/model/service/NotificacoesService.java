package com.vendabilhetes.model.service;


import com.vendabilhetes.model.Notificacoes;
import com.vendabilhetes.model.repository.NotificacoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacoesService {

    @Autowired
    private NotificacoesRepository notificacoesRepository;

    public List<Notificacoes> listarTodos() {
        return notificacoesRepository.findAll();
    }

    public Optional<Notificacoes> buscarPorId(Integer id) {
        return notificacoesRepository.findById(id);
    }

    public List<Notificacoes> buscarPorUsuario(Integer usuarioId) {
        return notificacoesRepository.findByUsuarioIdUsuario(usuarioId);
    }

    public Notificacoes salvar(Notificacoes notificacao) {
        return notificacoesRepository.save(notificacao);
    }

    public void deletar(Integer id) {
        notificacoesRepository.deleteById(id);
    }
}
