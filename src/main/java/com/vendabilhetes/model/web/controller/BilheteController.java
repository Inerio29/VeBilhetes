package com.vendabilhetes.model.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vendabilhetes.model.Bilhete;
import com.vendabilhetes.model.Bilhete.TipoBilhete;
import com.vendabilhetes.model.Evento;
import com.vendabilhetes.model.Pagamento;
import com.vendabilhetes.model.Usuario;
import com.vendabilhetes.model.service.BilheteService;
import com.vendabilhetes.model.service.EventoService;

@Controller
@RequestMapping("/bilhetes")
public class BilheteController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private BilheteService bilheteService;

    @GetMapping("/comprar")
    public String formCompra(@RequestParam Iterable<Integer> eventoId, Model model) {
        List<Evento> eventos = eventoService.buscarPorId(eventoId);
        model.addAttribute("eventos", eventos);
        model.addAttribute("bilhete", new Bilhete());
        return "comprar-bilhete";
    }

    @PostMapping("/comprar")
    public String comprar(@ModelAttribute Bilhete bilhete, Model model) {
        // Garantir que o 'evento' está presente
        Evento evento = bilhete.getEvento();
        if (evento == null || evento.getIdEvento() == null) {
            model.addAttribute("error", "Evento não selecionado.");
            return "comprar-bilhete"; // retornar ao formulário de compra
        }

        evento = eventoService.buscarPorId(List.of(evento.getIdEvento())).get(0); // buscar o evento no banco
        bilhete.setEvento(evento); // garantir que o evento está no bilhete

        // Garantir que o 'usuario' está preenchido corretamente (isto depende do teu sistema de login)
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1); // Por exemplo, pegar do contexto de segurança/autenticação
        bilhete.setUsuario(usuario);

        // Garantir que o 'pagamento' esteja corretamente preenchido
        Pagamento pagamento = new Pagamento();
        pagamento.setUsuario(usuario); // usuário que fez o pagamento
        pagamento.setAdministracao(null/* Aqui você precisa associar a administração correta */);
        pagamento.setMetodoPagamento(Pagamento.MetodoPagamento.CARTAO); // Exemplo de método de pagamento
        pagamento.setStatusPagamento(Pagamento.StatusPagamento.APROVADO); // Exemplo de status
        pagamento.setDataPagamento(new Date()); // Data do pagamento
        pagamento.setDescricao("Compra de bilhete");
        bilhete.setPagamento(pagamento); // associar o pagamento ao bilhete

        // Preencher o restante do Bilhete
        bilhete.setDataEmissao(new Date());
        bilhete.setTipoBilhete(Bilhete.TipoBilhete.NORMAL); // Ou escolher com base no formulário

        // Salvar o bilhete
        bilheteService.salvar(bilhete);

        // Adicionar o bilhete ao modelo e redirecionar
        model.addAttribute("bilhete", bilhete);
        return "redirect:/bilhetes/confirmado?id=" + bilhete.getIdBilhete();
    }

    @GetMapping("/confirmado")
    public String confirmado(@RequestParam Long id, Model model) {
        Bilhete bilhete = bilheteService.buscarPorId(id); // Buscar bilhete pelo id

        // Adicionar o bilhete e suas dependências ao modelo
        model.addAttribute("bilhete", bilhete);
        model.addAttribute("evento", bilhete.getEvento()); // Evento associado ao bilhete
        model.addAttribute("usuario", bilhete.getUsuario()); // Usuário associado ao bilhete
        model.addAttribute("preco", bilhete.getPreco()); // Preço do bilhete
        model.addAttribute("quantidadeIngressos", bilhete.getQuantidadeIngressos()); // Quantidade de ingressos
        model.addAttribute("descricaoBilhete", bilhete.getDescricaoBilhete()); // Descrição do bilhete

        return "bilhete-confirmado";
    }

    @GetMapping("/historico")
    public String historicoBilhetes(Model model) {
        model.addAttribute("bilhetes", bilheteService.listarTodos());
        return "historico-compras";
    }

    @GetMapping("/config")
    public String configurarBilhetes(Model model) {
        model.addAttribute("tiposBilhete", List.of(TipoBilhete.NORMAL, TipoBilhete.VIP));
        model.addAttribute("bilhete", new Bilhete());
        return "configB";
    }

    @PostMapping("/salvar")
    public String salvarBilhete(@ModelAttribute Bilhete bilhete, @RequestParam("eventoId") Integer eventoId) {
        Evento evento = eventoService.buscarPorId(List.of(eventoId)).stream()
                                     .findFirst()
                                     .orElse(null);
        if (evento == null) {
            return "redirect:/eventos/novo?erro=Evento não encontrado";
        }

        bilhete.setEvento(evento);
        bilheteService.salvar(bilhete);
        return "redirect:/dashpro";
    }
}
