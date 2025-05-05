package com.vendabilhetes.model;

import jakarta.persistence.*;

import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Administracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAdministracao;
    
    

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "idUsuario", nullable = false) // Garantindo a relação correta
    private Usuario usuario;

    @Column(length = 255)
    private String descricao;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao = new Date();

	public Integer getIdAdministracao() {
		return idAdministracao;
	}

	public void setIdAdministracao(Integer idAdministracao) {
		this.idAdministracao = idAdministracao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
    
    
}
