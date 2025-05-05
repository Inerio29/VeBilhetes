package com.vendabilhetes.model.web.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vendabilhetes.model.Administracao;
import com.vendabilhetes.model.Evento;
import com.vendabilhetes.model.Usuario;
import com.vendabilhetes.model.repository.EventoRepository;
import com.vendabilhetes.model.service.AdministracaoService;
import com.vendabilhetes.model.service.EventoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/eventos")
public class EventoController {
    @Autowired
    private EventoService eventoService;

    @Autowired
    private AdministracaoService administracaoService;

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/novo")
    public String novoEvento(Model model) {
        // Inicializa um novo evento
        Evento evento = new Evento();
        model.addAttribute("evento", evento);
        return "novoE";
    }

    @PostMapping("/salvar")
    @ResponseBody
    public ResponseEntity<?> salvarEventoComImagem(
            @RequestParam("titulo") String titulo,
            @RequestParam("categoria") String categoria,
            @RequestParam("tipoEvento") String tipoEvento,
            @RequestParam("descricao") String descricao,
            @RequestParam("dataInicioStr") String dataInicioStr,
            @RequestParam("horaInicioStr") String horaInicioStr,
            @RequestParam("local") String local,
            @RequestParam("endereco") String endereco,
            @RequestParam("imagemFile") MultipartFile imagemFile,
            @RequestParam("preco") Double preco,
            @RequestParam("quantidadeIngressos") Integer quantidadeIngressos,
            @RequestParam(value = "descricaoBilhete", required = false) String descricaoBilhete,
            HttpSession session
    ) {
        try {
            // Recuperar usuário da sessão
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuário não autenticado.");
            }

            // Verificar se é promotor aprovado
            if (usuario.getTipoUsuario() != Usuario.TipoUsuario.PROMOTOR &&
                    usuario.getTipoUsuario() != Usuario.TipoUsuario.ADMIN) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Apenas promotores aprovados ou administradores podem criar eventos.");
            }

            // Criar novo evento
            Evento evento = new Evento();
            evento.setTitulo(titulo);
            evento.setCategoria(categoria);
            evento.setTipoEvento(tipoEvento);
            evento.setDescricao(descricao);
            evento.setLocal(local);
            evento.setEndereco(endereco);

            // Conversão de data e hora
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                LocalDate dataInicio = LocalDate.parse(dataInicioStr, dateFormatter);
                LocalTime horaInicio = LocalTime.parse(horaInicioStr, timeFormatter);

                evento.setDataInicio(dataInicio);
                evento.setHoraInicio(horaInicio);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Formato de data ou hora inválido: " + e.getMessage());
            }

            // Processar a imagem
            if (imagemFile != null && !imagemFile.isEmpty()) {
                try {
                    byte[] imagemBytes = imagemFile.getBytes();
                    evento.setImagem(imagemBytes);
                } catch (IOException e) {
                    return ResponseEntity.badRequest().body("Erro ao processar a imagem: " + e.getMessage());
                }
            }

            // Associar o usuário promotor
            evento.setUsuario(usuario);

            // Associar administração
            try {
                Administracao adm = administracaoService.obter();
                adm.setUsuario(usuario);
                evento.setAdministracao(adm);
            } catch (RuntimeException e) {
                Administracao novaAdm = new Administracao();
                novaAdm.setUsuario(usuario);
                administracaoService.salvar(novaAdm);
                evento.setAdministracao(novaAdm);
            }

            // Definir status (se não estiver definido)
            if (evento.getStatus() == null) {
                evento.setStatus(Evento.Status.AGENDADO);
            }

            // Associar informações do bilhete ao evento
            evento.setPreco(preco);
            evento.setQuantidadeIngressos(quantidadeIngressos);
            
            if (descricaoBilhete != null && !descricaoBilhete.isEmpty()) {
                evento.setDescricaoBilhete(descricaoBilhete);
            }

            // Salvar evento
            eventoService.salvar(evento);
            return ResponseEntity.ok("Evento criado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao criar o evento: " + e.getMessage());
        }
    }

    @GetMapping("/editar")
    public String editarEvento(@RequestParam("id") Integer id, Model model) {
        Evento evento = eventoService.buscarPorId(List.of(id)).stream()
                .findFirst()
                .orElse(null);
        if (evento == null) {
            return "redirect:/usuarios/admin/gestao-eventos";
        }
        model.addAttribute("evento", evento);
        return "editar-evento";
    }

    @PostMapping("/atualizar")
    public String atualizarEvento(
            @ModelAttribute Evento evento,
            @RequestParam(value = "imagemFile", required = false) MultipartFile imagemFile,
            @RequestParam("dataInicioStr") String dataInicioStr,
            @RequestParam("horaInicioStr") String horaInicioStr,
            @RequestParam(value = "preco", required = false) Double preco,
            @RequestParam(value = "quantidadeIngressos", required = false) Integer quantidadeIngressos,
            @RequestParam(value = "descricaoBilhete", required = false) String descricaoBilhete,
            RedirectAttributes redirectAttributes) {

        try {
            // Busca o evento original
            Evento eventoOriginal = eventoService.buscarPorId(List.of(evento.getIdEvento())).get(0);

            // Atualizar os campos básicos
            eventoOriginal.setTitulo(evento.getTitulo());
            eventoOriginal.setDescricao(evento.getDescricao());
            eventoOriginal.setCategoria(evento.getCategoria());
            eventoOriginal.setTipoEvento(evento.getTipoEvento());
            eventoOriginal.setLocal(evento.getLocal());
            eventoOriginal.setEndereco(evento.getEndereco());

            // Conversão de data e hora
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDate dataInicio = LocalDate.parse(dataInicioStr, dateFormatter);
            LocalTime horaInicio = LocalTime.parse(horaInicioStr, timeFormatter);

            eventoOriginal.setDataInicio(dataInicio);
            eventoOriginal.setHoraInicio(horaInicio);

            // Atualizar informações do bilhete
            if(preco != null) {
                eventoOriginal.setPreco(preco);
            }
            
            if(quantidadeIngressos != null) {
                eventoOriginal.setQuantidadeIngressos(quantidadeIngressos);
            }
            
            if(descricaoBilhete != null) {
                eventoOriginal.setDescricaoBilhete(descricaoBilhete);
            }

            // Processar imagem
            if (imagemFile != null && !imagemFile.isEmpty()) {
                try {
                    byte[] imagemBytes = imagemFile.getBytes();
                    eventoOriginal.setImagem(imagemBytes);
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("error", "Erro ao salvar a imagem: " + e.getMessage());
                    return "redirect:/eventos/editar?id=" + evento.getIdEvento();
                }
            }

            // Atualizar evento
            eventoService.salvar(eventoOriginal);
            redirectAttributes.addFlashAttribute("success", "Evento atualizado com sucesso!");
            return "redirect:/usuarios/admin/gestao-eventos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar evento: " + e.getMessage());
            return "redirect:/eventos/editar?id=" + evento.getIdEvento();
        }
    }
    @GetMapping("/api/events")
    @ResponseBody
    public List<Evento> getEvents() {
        // Retorna todos os eventos disponíveis
        return eventoRepository.findAll();
    }
    @GetMapping("/detalhes-eventos")
    public String detalhesEvento(@RequestParam("id") Integer id, Model model) {
        Evento evento = eventoService.buscarPorId(List.of(id))
                          .stream()
                          .findFirst()
                          .orElse(null);
        
        if (evento == null) {
            return "redirect:/erro"; // Ou uma página de erro customizada
        }

        model.addAttribute("evento", evento);
        return "detalhes-eventos"; // Nome do HTML (detalhes-eventos.html)
    }


}