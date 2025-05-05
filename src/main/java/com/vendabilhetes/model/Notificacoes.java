package com.vendabilhetes.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Notificacoes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNotificacoes;

    @ManyToOne
    @JoinColumn(name = "Usuario_idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "Evento_idEvento", nullable = false)
    private Evento evento;

    @Lob
    private String mensagem;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Tipo {
        EMAIL, PUSH, SMS, IN_APP
    }

    public enum Prioridade {
        BAIXA, MEDIA, ALTA
    }

    public enum Status {
        ENVIADA, LIDA, IGNORADA
    }
}
