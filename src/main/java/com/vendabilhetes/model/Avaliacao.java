package com.vendabilhetes.model;

import jakarta.persistence.*;

import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAvaliacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Lob
    private String comentario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAvaliacao = new Date(); // Preenche automaticamente

    @Enumerated(EnumType.STRING)
    private Visibilidade visibilidade;

    public enum Visibilidade {
        PUBLICA, PRIVADA
    }
}
