package com.vendabilhetes.model.service;

import com.vendabilhetes.model.Usuario;
import com.vendabilhetes.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ——————————————————————————————
    // Autenticação
    // ——————————————————————————————
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }

    // ——————————————————————————————
    // CRUD Genérico
    // ——————————————————————————————
    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // ——————————————————————————————
    // Cadastro com validação de e‑mail único
    // ——————————————————————————————
    public Usuario cadastrarUsuario(Usuario novoUsuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(novoUsuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Este e‑mail já está cadastrado.");
        }
        return usuarioRepository.save(novoUsuario);
    }

    // ——————————————————————————————
    // Fluxo de Pré‑registro de Promotor
    // ——————————————————————————————

    /**
     * Cria um pré‑cadastro para um novo promotor, com estadoConta = PENDENTE.
     */
    public Usuario preRegistrarPromotor(Usuario novoPromotor) {
        novoPromotor.setTipoUsuario(Usuario.TipoUsuario.PROMOTOR);
        novoPromotor.setEstadoConta(Usuario.EstadoConta.PENDENTE);
        return usuarioRepository.save(novoPromotor);
    }
    public void preRegistarPromotor(Usuario usuario) {
        usuario.setTipoUsuario(Usuario.TipoUsuario.PROMOTOR);
        usuario.setEstadoConta(Usuario.EstadoConta.PENDENTE);
        usuarioRepository.save(usuario);
    }

// Lista todos os promotores
    public List<Usuario> listarPromotoresPendentes() {
        List<Usuario> pendentes = usuarioRepository.findByTipoUsuarioAndEstadoConta(
            Usuario.TipoUsuario.PROMOTOR,
            Usuario.EstadoConta.PENDENTE
        );
        return pendentes != null ? pendentes : List.of();
    }


    
     //Aprova o promotor com o ID informado, alterando seu estadoConta para APROVADO.
     
    public Usuario aprovarPromotor(Integer idPromotor) {
        Usuario promotor = usuarioRepository.findById(idPromotor)
            .orElseThrow(() -> new NoSuchElementException("Promotor não encontrado: " + idPromotor));
        promotor.setEstadoConta(Usuario.EstadoConta.APROVADO);
        return usuarioRepository.save(promotor);
    }

    
     //Rejeita o promotor com o ID informado, alterando seu estadoConta para REJEITADO.
     
    public Usuario rejeitarPromotor(Integer idPromotor) {
        Usuario promotor = usuarioRepository.findById(idPromotor)
            .orElseThrow(() -> new NoSuchElementException("Promotor não encontrado: " + idPromotor));
        promotor.setEstadoConta(Usuario.EstadoConta.REJEITADO);
        return usuarioRepository.save(promotor);
    }

    // ——————————————————————————————
    // Contadores
    // ——————————————————————————————
    public long count() {
        return usuarioRepository.count();
    }

    public long countPromotoresPendentes() {
        return usuarioRepository.countByTipoUsuarioAndEstadoConta(
            Usuario.TipoUsuario.PROMOTOR,
            Usuario.EstadoConta.PENDENTE
        );
    }
    
    public List<Usuario> listarTodosOsPromotores() {
        List<Usuario> promotores = usuarioRepository.findByTipoUsuario(Usuario.TipoUsuario.PROMOTOR);
        return promotores != null ? promotores : List.of(); // Segurança extra
    }

    public void eliminarPromotor(Integer id) {
        usuarioRepository.deleteById(id);
    }



}
