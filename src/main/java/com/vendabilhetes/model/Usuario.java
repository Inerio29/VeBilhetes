package com.vendabilhetes.model;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario {

    // === Atributos ===

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario; // Chave primária única

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(length = 20)
    private String telefone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoConta estadoConta = EstadoConta.PENDENTE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro = new Date();

	@SuppressWarnings("unused")
	private boolean ativo;

    // === Enums ===

    public enum TipoUsuario {
        ADMIN,
        ESPECTADOR,
        PROMOTOR
    }

    public enum EstadoConta {
        PENDENTE,
        APROVADO,
        REJEITADO
    }

    // === Getters e Setters ===

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public EstadoConta getEstadoConta() {
        return estadoConta;
    }

    public void setEstadoConta(EstadoConta estadoConta) {
        this.estadoConta = estadoConta;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @SuppressWarnings("unused")
	private String confirmarSenha;

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }   

    // Getters e setters
    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getClasseEstado() {
        switch (this.estadoConta) {
            case APROVADO: return "badge-ativo";
            case REJEITADO: return "badge-inativo";
            default: return "badge-warning";
        }
    }

    @Lob
    @Column(name = "imagem_url")
    private byte[] imagemUrl;

    public byte[] getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(byte[] imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
