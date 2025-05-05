package com.vendabilhetes.model.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.vendabilhetes.model.Evento;
import com.vendabilhetes.model.Usuario;
@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer> {
    List<Evento> findByTituloContainingIgnoreCase(String titulo);
    List<Evento> findByCategoria(String categoria);
    List<Evento> findByStatus(Evento.Status status);
    List<Evento> findAll();
    
    // Novo método para buscar eventos por ID do usuário (promotor)
    List<Evento> findByUsuarioIdUsuario(Integer usuarioId);
    
    @Query("SELECT COUNT(e) FROM Evento e WHERE e.usuario.tipoUsuario = 'PROMOTOR'")
    long countByTipoPromotor();
    
    @Query("SELECT COUNT(e) FROM Evento e WHERE e.usuario.tipoUsuario = :tipo")
    long countByTipoUsuario(@Param("tipo") Usuario.TipoUsuario tipoUsuario);
    
    @Query("SELECT e FROM Evento e WHERE e.usuario.tipoUsuario = 'PROMOTOR' AND e.dataInicio > CURRENT_TIMESTAMP ORDER BY e.dataInicio ASC")
    List<Evento> findProximosEventosDoPromotor();
    
    // Suponha que o campo "ocupacao" exista, substitui "occupancyRate"
    @Query("SELECT AVG(e.ocupacao) FROM Evento e WHERE e.usuario.tipoUsuario = 'PROMOTOR'")
    Double calculateAverageOccupancyForPromotor();
    
    @Query("SELECT l.message FROM Log l ORDER BY l.timestamp DESC")
    List<String> findRecentLogs();
    
    @Query("SELECT e FROM Evento e WHERE e.usuario.tipoUsuario = :tipo AND e.dataInicio > CURRENT_TIMESTAMP ORDER BY e.dataInicio ASC")
    List<Evento> findProximosEventosByTipo(@Param("tipo") Usuario.TipoUsuario tipoUsuario);
    Optional<Evento> findById(Integer id);
}