package com.vendabilhetes.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class EventoResponse {
    private Integer id;
    private String titulo;
    private String descricao;
    private String dataInicio;
    private String horaInicio;
    private String local;
    private String categoria;
    private Evento.Status status;
    private String icon;        // Novo campo para o ícone
    private String iconColor;   // Novo campo para a cor do ícone
    private String preco;       // Campo para exibir o preço (opcional)

    // Construtor existente
    public EventoResponse(Integer id, String titulo, String descricao, LocalDate dataInicio, 
                          LocalTime horaInicio, String local, String categoria, Evento.Status status) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        
        // Formatar data e hora
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        this.dataInicio = dataInicio != null ? dataInicio.format(dateFormatter) : "";
        this.horaInicio = horaInicio != null ? horaInicio.format(timeFormatter) : "";
        
        this.local = local;
        this.categoria = categoria;
        this.status = status;
        this.preco = "A partir de MT 500"; // Valor padrão, pode ser atualizado depois
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
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

    public Evento.Status getStatus() {
        return status;
    }

    public void setStatus(Evento.Status status) {
        this.status = status;
    }

    // Novos getters e setters para icon e iconColor
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }
    
    // Getter e setter para preço
    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}