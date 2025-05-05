package com.vendabilhetes.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.vendabilhetes.model.ConfiguracaoSistema;

public interface ConfiguracaoRepository extends JpaRepository<ConfiguracaoSistema, Long> {
}

