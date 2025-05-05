package com.vendabilhetes.model.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vendabilhetes.model.Evento;
import com.vendabilhetes.model.Usuario;
import com.vendabilhetes.model.repository.EventoRepository;
import com.vendabilhetes.model.repository.UsuarioRepository;
@Service
public class EventoService {
    @Autowired
    private EventoRepository eventoRepository;
    
    // Injeção do repositório de usuários
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }
    
    public List<Evento> buscarPorId(Iterable<Integer> ids) {
        return eventoRepository.findAllById(ids);
    }
 // No EventoService.java
    public Evento buscarPorId(Integer id) {
        return eventoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
    }
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email); // Aqui, você precisa ter esse método no seu repository
    }
    
    public Evento salvar(Evento evento) {
        return eventoRepository.save(evento);
    }
    
    public void deletar(Integer id) {
        eventoRepository.deleteById(id);
    }
    
    public List<Evento> buscarPorTitulo(String titulo) {
        return eventoRepository.findByTituloContainingIgnoreCase(titulo);
    }
    
    public List<Evento> buscarPorCategoria(String categoria) {
        return eventoRepository.findByCategoria(categoria);
    }
    
    public long count() {
        return eventoRepository.count();
    }
    
    public long countByPromotor() {
        return eventoRepository.countByTipoPromotor();
    }
    
    public long countByTipoUsuario(Usuario.TipoUsuario tipoUsuario) {
        return eventoRepository.countByTipoUsuario(tipoUsuario);
    }
    
    public List<Evento> getProximosEventosDoPromotor() {
        return eventoRepository.findProximosEventosDoPromotor();
    }
    
    public List<String> getLogsRecentes() {
        return eventoRepository.findRecentLogs();
    }
    
    public double getOcupacaoMediaPorPromotor() {
        Double media = eventoRepository.calculateAverageOccupancyForPromotor();
        return media != null ? media : 0.0;
    }
    
    public List<Evento> buscarProximosEventosPorTipo(Usuario.TipoUsuario tipoUsuario, String... filtros) {
        return eventoRepository.findProximosEventosByTipo(tipoUsuario);
    }
    
    private Status status;
    
    public enum Status {
        AGENDADO,
        CONCLUÍDO,
        CANCELADO
    }
    
    // Getters e Setters para status
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public List<Evento> buscarTodosEventos() {
        // Supondo que o EventoRepository estenda JpaRepository
        return eventoRepository.findByStatus(Evento.Status.AGENDADO);
    }
    
    // Novo método para buscar eventos por promotor ID
    public List<Evento> buscarEventosPorPromotorId(Integer promotorId) {
        return eventoRepository.findByUsuarioIdUsuario(promotorId);
    }
}