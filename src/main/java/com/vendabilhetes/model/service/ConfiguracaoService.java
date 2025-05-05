package com.vendabilhetes.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendabilhetes.model.ConfiguracaoSistema;
import com.vendabilhetes.model.repository.ConfiguracaoRepository;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository repository;

    public ConfiguracaoSistema getConfiguracaoAtual() {
        // Assumimos que há apenas uma configuração. Se não houver, criamos uma padrão.
        Optional<ConfiguracaoSistema> config = repository.findAll().stream().findFirst();

        return config.orElseGet(() -> {
            ConfiguracaoSistema nova = new ConfiguracaoSistema();
            nova.setNomeSistema("VeBilhetes");
            nova.setEmailSuporte("suporte@vebilhetes.co.mz");
            nova.setComissaoVenda(5.0);
            return repository.save(nova);
        });
    }

    public void salvar(ConfiguracaoSistema config) {
        repository.save(config);
    }
}
