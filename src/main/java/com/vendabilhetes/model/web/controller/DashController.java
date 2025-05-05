package com.vendabilhetes.model.web.controller;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.vendabilhetes.model.Evento;
import com.vendabilhetes.model.Usuario;
import com.vendabilhetes.model.service.BilheteService;
import com.vendabilhetes.model.service.EventoService;
import com.vendabilhetes.model.service.UsuarioService;

@Controller
public class DashController {
	
	 @Autowired
	    private UsuarioService usuarioService;
	    
	    @Autowired
	   
	    

    private final EventoService eventoService;
    private final BilheteService bilheteService;
    public DashController(EventoService eventoService,
                          BilheteService bilheteService,
                          UsuarioService usuarioService) {
        this.eventoService = eventoService;
        this.bilheteService = bilheteService;
        this.usuarioService = usuarioService;
    }

    // Dashboard Promotor
    @GetMapping("/dashpro")
    public String dashboardPromotor(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null || usuario.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        switch (usuario.getEstadoConta()) {
            case PENDENTE:
                return "pendente";
            case REJEITADO:
                session.invalidate();
                return "redirect:/usuarios/login";
            default:
                // APROVADO continua
        }

        int promotorId = usuario.getIdUsuario();
        model.addAttribute("totalEventos",     eventoService.countByPromotor());
        model.addAttribute("bilhetesVendidos", bilheteService.countVendidosPorPromotor());
        model.addAttribute("taxaOcupacao",     eventoService.getOcupacaoMediaPorPromotor());
        model.addAttribute("proximosEventos",  eventoService.getProximosEventosDoPromotor());
        model.addAttribute("receitaTotal",     bilheteService.getReceitaPorPromotor(promotorId));

        // Complementos visuais
        model.addAttribute("crescimentoBilhetes",    "15%");
        model.addAttribute("crescimentoReceita",     "8%");
        model.addAttribute("eventosNovosDescricao",  "2 novos");
        model.addAttribute("tituloEventos",          "Eventos Ativos");
        model.addAttribute("tituloBilhetes",         "Bilhetes Vendidos");
        model.addAttribute("tituloReceita",          "Receita Total");
        model.addAttribute("tituloOcupacao",         "Taxa de Ocupação");
        model.addAttribute("descricaoOcupacao",      "Média dos eventos");

        return "dashpro";
    }
    
    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        // Buscar apenas os eventos do promotor logado
        List<Evento> eventosDoPromotor = eventoService.buscarEventosPorPromotorId(usuarioLogado.getIdUsuario());
        model.addAttribute("eventos", eventosDoPromotor);
        model.addAttribute("promotor", usuarioLogado);
        
        return "dashpro";
    }
    
    @GetMapping("/eventos")
    public String meusEventos(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        List<Evento> eventosDoPromotor = eventoService.buscarEventosPorPromotorId(usuarioLogado.getIdUsuario());
        model.addAttribute("eventos", eventosDoPromotor);
        
        return "eventos-promotor";
    }
    
    @GetMapping("/evento/novo")
    public String novoEvento(Model model) {
        model.addAttribute("evento", new Evento());
        return "novo-evento";
    }
    
    @PostMapping("/evento/salvar")
    public String salvarEvento(@ModelAttribute Evento evento, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        // Associar o evento ao promotor logado
        evento.setUsuario(usuarioLogado);
        eventoService.salvar(evento);
        
        return "redirect:/dashpro/eventos";
    }
    
    @GetMapping("/evento/editar/{id}")
    public String editarEvento(@PathVariable Integer id, HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        Evento evento = eventoService.buscarPorId(List.of(id)).get(0);
        
        // Verificar se o evento pertence ao promotor logado
        if (evento.getUsuario() == null || !evento.getUsuario().getIdUsuario().equals(usuarioLogado.getIdUsuario())) {
            return "redirect:/dashpro/eventos";
        }
        
        model.addAttribute("evento", evento);
        return "editar-evento";
    }
    
    @PostMapping("/evento/excluir/{id}")
    public String excluirEvento(@PathVariable Integer id, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR) {
            return "redirect:/usuarios/login";
        }
        
        Evento evento = eventoService.buscarPorId(List.of(id)).get(0);
        
        // Verificar se o evento pertence ao promotor logado
        if (evento.getUsuario() != null && evento.getUsuario().getIdUsuario().equals(usuarioLogado.getIdUsuario())) {
            eventoService.deletar(id);
        }
        
        return "redirect:/dashpro/eventos";
    }
    
    @GetMapping("/dashAD")
    public String dashboardAdmin(Model model) {
        // Adicionando dados para o dashboard
        model.addAttribute("totalEventos", eventoService.count());
        model.addAttribute("totalUsuarios", usuarioService.count());
        model.addAttribute("vendasHoje", 0); // Substitua por um serviço real de vendas quando disponível
        model.addAttribute("pendentes", usuarioService.countPromotoresPendentes());
        
        return "dashAD";
    }
}
    