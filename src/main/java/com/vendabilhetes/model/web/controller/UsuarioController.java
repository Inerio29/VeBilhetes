package com.vendabilhetes.model.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vendabilhetes.model.Evento;
import com.vendabilhetes.model.Usuario;
import com.vendabilhetes.model.service.EventoService;
import com.vendabilhetes.model.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoService eventoService;

    // === LOGIN ===
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String senha,
                        Model model,
                        HttpSession session) {
        Usuario usuario = usuarioService.autenticar(email, senha);
        if (usuario != null) {
            session.setAttribute("usuarioLogado", usuario);
            switch (usuario.getTipoUsuario()) {
                case PROMOTOR:
                    switch (usuario.getEstadoConta()) {
                        case APROVADO:
                            return "redirect:/dashpro";
                        case PENDENTE:
                            model.addAttribute("mensagem", "Seu cadastro está pendente de aprovação.");
                            return "pendente";
                        case REJEITADO:
                            model.addAttribute("erro", "Seu cadastro foi rejeitado.");
                            return "login";
                    }
                case ESPECTADOR:
                    return "redirect:/home";
                case ADMIN:
                    return "redirect:/dashAD";
            }
        }
        model.addAttribute("erro", "Credenciais inválidas");
        return "login";
    }

    // === CADASTRO ===
    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String processarCadastro(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.salvar(usuario);
        return "redirect:/usuarios/login";
    }

    // === HOME ESPECTADOR ===
    @GetMapping("/home")
    public String homeEspectador() {
        return "home";
    }

    // === DASHBOARD PROMOTOR ===
    @GetMapping("/promotor/dashboard")
    public String dashPromotor(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        // Buscar apenas os eventos do promotor logado
        List<Evento> eventosDoPromotor = eventoService.buscarEventosPorPromotorId(usuarioLogado.getIdUsuario());
        model.addAttribute("eventos", eventosDoPromotor);
        return "dashpro";
    }
    
    // === EVENTOS DO PROMOTOR ===
    @GetMapping("/promotor/eventos")
    public String eventosPromotor(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        List<Evento> eventosDoPromotor = eventoService.buscarEventosPorPromotorId(usuarioLogado.getIdUsuario());
        model.addAttribute("eventos", eventosDoPromotor);
        return "eventos-promotor";
    }
    
    // === CRIAR EVENTO (PROMOTOR) ===
    @GetMapping("/promotor/evento/novo")
    public String novoEventoForm(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        model.addAttribute("evento", new Evento());
        return "novo-evento";
    }
    
    @PostMapping("/promotor/evento/salvar")
    public String salvarEvento(@ModelAttribute Evento evento, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        // Associar o evento ao promotor logado
        evento.setUsuario(usuarioLogado);
        eventoService.salvar(evento);
        return "redirect:/usuarios/promotor/eventos";
    }
    
    // === EDITAR EVENTO (PROMOTOR) ===
    @GetMapping("/promotor/evento/editar/{id}")
    public String editarEventoPromotor(@PathVariable Integer id, HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        Evento evento = eventoService.buscarPorId(List.of(id)).get(0);
        
        // Verificar se o evento pertence ao promotor logado
        if (evento.getUsuario() == null || !evento.getUsuario().getIdUsuario().equals(usuarioLogado.getIdUsuario())) {
            return "redirect:/usuarios/promotor/eventos";
        }
        
        model.addAttribute("evento", evento);
        return "editar-evento";
    }
    
    // === EXCLUIR EVENTO (PROMOTOR) ===
    @PostMapping("/promotor/evento/excluir/{id}")
    public String excluirEventoPromotor(@PathVariable Integer id, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        Evento evento = eventoService.buscarPorId(List.of(id)).get(0);
        
        // Verificar se o evento pertence ao promotor logado
        if (evento.getUsuario() != null && evento.getUsuario().getIdUsuario().equals(usuarioLogado.getIdUsuario())) {
            eventoService.deletar(id);
        }
        
        return "redirect:/usuarios/promotor/eventos";
    }

    // === GESTÃO DE EVENTOS (ADMIN) ===
    @GetMapping("/admin/gestao-eventos")
    public String gestaoEventos(Model model) {
        List<Evento> eventos = eventoService.listarTodos();
        model.addAttribute("eventos", eventos);
        return "gestaoE";
    }

    @GetMapping("/editar/{id}")
    public String editarEvento(@PathVariable Integer id, Model model) {
        Evento evento = eventoService.buscarPorId(List.of(id)).get(0);
        model.addAttribute("evento", evento);
        return "editar-evento";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEvento(@PathVariable Integer id) {
        eventoService.deletar(id);
        return "redirect:/usuarios/admin/gestao-eventos";
    }

    @GetMapping("/detalhes")
    public String detalhesEvento(@RequestParam("id") Integer id, Model model) {
        Evento evento = eventoService.buscarPorId(List.of(id)).get(0);
        model.addAttribute("evento", evento);
        return "detalhes-evento";
    }

    // === GESTÃO DE UTILIZADORES (ADMIN) ===
    @GetMapping("/admin/gestao-utilizadores")
    public String gestaoUtilizadores(Model model) {
        List<Usuario> promotores = usuarioService.listarTodosOsPromotores();
        List<Usuario> pendentes = usuarioService.listarPromotoresPendentes();
        model.addAttribute("promotores", promotores);
        model.addAttribute("pendentes", pendentes);
        return "gestaoUt";
    }

    @PostMapping("/admin/aprovar-promotor/{id}")
    public String aprovar(@PathVariable("id") Integer id) {
        usuarioService.aprovarPromotor(id);
        return "redirect:/usuarios/admin/gestao-utilizadores";
    }

    @PostMapping("/admin/rejeitar-promotor/{id}")
    public String rejeitar(@PathVariable("id") Integer id) {
        usuarioService.rejeitarPromotor(id);
        return "redirect:/usuarios/admin/gestao-utilizadores";
    }

    @PostMapping("/admin/eliminar-promotor/{id}")
    public String eliminarPromotor(@PathVariable("id") Integer id) {
        usuarioService.eliminarPromotor(id);
        return "redirect:/usuarios/admin/gestao-utilizadores";
    }

    // === PÁGINAS ADMINISTRATIVAS ===
    @GetMapping("/admin/relatorios")
    public String relatorios() {
        return "relatorioE";
    }

    @GetMapping("/admin/configuracoes")
    public String configuracoesSistema() {
        return "configS";
    }

    @GetMapping("/admin/dashboard")
    public String dashboardAdmin() {
        return "dashAD";
    }

    @GetMapping("/admin/novo-admin")
    public String mostrarFormularioNovoAdmin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "novo-admin";
    }

    @PostMapping("/admin/novo-admin")
    public String salvarNovoAdmin(@ModelAttribute("usuario") Usuario usuario, Model model) {
        if (!usuario.getSenha().equals(usuario.getConfirmarSenha())) {
            model.addAttribute("erro", "As senhas não coincidem");
            return "novo-admin";
        }
        usuario.setTipoUsuario(Usuario.TipoUsuario.ADMIN);
        usuarioService.salvar(usuario);
        return "redirect:/usuarios/login";
    }

    @GetMapping("/admin/cadastro-admin")
    public String mostrarFormularioCadastroAdmin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro-admin";
    }

    @PostMapping("/admin/cadastro-admin")
    public String cadastrarNovoAdmin(@ModelAttribute("usuario") Usuario usuario, Model model) {
        if (!usuario.getSenha().equals(usuario.getConfirmarSenha())) {
            model.addAttribute("erro", "As senhas não coincidem.");
            return "cadastro-admin";
        }
        usuario.setTipoUsuario(Usuario.TipoUsuario.ADMIN);
        usuarioService.salvar(usuario);
        return "redirect:/usuarios/login";
    }
    
    // === LOGOUT ===
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuarios/login";
    }
}