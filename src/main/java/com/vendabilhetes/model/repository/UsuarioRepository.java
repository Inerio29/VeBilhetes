package com.vendabilhetes.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vendabilhetes.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByTipoUsuarioAndEstadoConta(Usuario.TipoUsuario tipo, Usuario.EstadoConta estado);
    long countByTipoUsuarioAndEstadoConta(Usuario.TipoUsuario tipo, Usuario.EstadoConta estado);
    List<Usuario> findByTipoUsuario(Usuario.TipoUsuario tipoUsuario);
 // Adicione este método para buscar o usuário por email
   // Usuario findByEmail(String email);

}
