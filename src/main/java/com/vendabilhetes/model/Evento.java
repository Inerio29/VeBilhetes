package com.vendabilhetes.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvento;

    @ManyToOne
    @JoinColumn(name = "Administracao_idAdministracao", nullable = false)
    private Administracao administracao;

    @ManyToOne
    @JoinColumn(name = "Usuario_idUsuario", nullable = false)
    private Usuario usuario;

    @Column(length = 150, nullable = false)
    private String titulo;

    private Double preco;

    @Lob
    private String descricao;

    @Column(length = 50)
    private String tipoEvento;

    @Column
    private LocalDate dataInicio;

    @Column
    private LocalTime horaInicio;

    @Column(length = 200)
    private String local;

    @Column(length = 100)
    private String categoria;

    @Column
    private Double ocupacao;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Lob
    @Column(name = "imagem", columnDefinition = "LONGBLOB")
    private byte[] imagem;

    @Column(length = 255)
    private String endereco;

    private String imagemUrl;

    @Column
    private Integer quantidadeIngressos; // Quantidade de ingressos disponíveis

    @Column(length = 500)
    private String descricaoBilhete; // Descrição detalhada do ingresso

    public enum Status {
        AGENDADO, CANCELADO, FINALIZADO, EM_ANDAMENTO
    }

    public Evento() {}

    public Evento(Integer idEvento, Administracao administracao, Usuario usuario, String titulo, String descricao,
                  String local, String categoria, byte[] imagem, Status status, LocalDate dataInicio, LocalTime horaInicio) {
        this.idEvento = idEvento;
        this.administracao = administracao;
        this.usuario = usuario;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.local = local;
        this.categoria = categoria;
        this.imagem = imagem;
        this.status = status;
        this.horaInicio = horaInicio;
    }

    public Evento(Double ocupacao) {
        this.ocupacao = ocupacao;
    }

    public Double getPercentualOcupado() {
        return ocupacao != null ? ocupacao : 0.0;
    }

    public void setPercentualOcupado(Double percentualOcupado) {
        this.ocupacao = percentualOcupado;
    }

    // Getters e Setters
    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Administracao getAdministracao() {
        return administracao;
    }

    public void setAdministracao(Administracao administracao) {
        this.administracao = administracao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(Double ocupacao) {
        this.ocupacao = ocupacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
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

    @Override
    public String toString() {
        return "Evento{" +
                "idEvento=" + idEvento +
                ", titulo='" + titulo + '\'' +
                ", dataInicio=" + dataInicio +
                ", local='" + local + '\'' +
                ", categoria='" + categoria + '\'' +
                ", status=" + status +
                '}';
    }
}
