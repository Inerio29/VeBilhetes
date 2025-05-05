package com.vendabilhetes.model;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Bilhete {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bilhete")
	private Long idBilhete;
	
	 private Double preco;  // Novo campo
	 private Integer quantidadeIngressos;  // Novo campo
	 private String descricaoBilhete;  // Novo campo

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_pagamento", nullable = false)
    private Pagamento pagamento;
    
    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @Column(length = 50, unique = true)
    private String codigoBilhete;

    @Enumerated(EnumType.STRING)
    private TipoBilhete tipoBilhete;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEmissao;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum TipoBilhete {
        NORMAL, VIP
    }

    public enum Status {
        EMITIDO, USADO, CANCELADO
    }

    public Bilhete() {
        this.status = Status.EMITIDO;
    }

    public Bilhete(Usuario usuario, Pagamento pagamento, Evento evento,
                   String codigoBilhete, TipoBilhete tipoBilhete, Date dataEmissao, Status status) {
        this.usuario = usuario;
        this.pagamento = pagamento;
        this.evento = evento;
        this.codigoBilhete = codigoBilhete;
        this.tipoBilhete = tipoBilhete;
        this.dataEmissao = dataEmissao;
        this.status = status;
    }

	public Long getIdBilhete() {
		return idBilhete;
	}

	public void setIdBilhete(Long idBilhete) {
		this.idBilhete = idBilhete;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public String getCodigoBilhete() {
		return codigoBilhete;
	}

	public void setCodigoBilhete(String codigoBilhete) {
		this.codigoBilhete = codigoBilhete;
	}

	public TipoBilhete getTipoBilhete() {
		return tipoBilhete;
	}

	public void setTipoBilhete(TipoBilhete tipoBilhete) {
		this.tipoBilhete = tipoBilhete;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Double getPreco() {
	    return preco;
	}

	public void setPreco(Double preco) {
	    this.preco = preco;
	}

	public Integer getQuantidadeIngressos() {
	    return quantidadeIngressos;
	}

	public void setQuantidadeIngressos(Integer quantidadeIngressos) {
	    this.quantidadeIngressos = quantidadeIngressos;
	}

	public String getDescricaoBilhete() {
	    return descricaoBilhete;
	}

	public void setDescricaoBilhete(String descricaoBilhete) {
	    this.descricaoBilhete = descricaoBilhete;
	}
    
}
