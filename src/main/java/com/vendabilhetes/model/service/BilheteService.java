package com.vendabilhetes.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vendabilhetes.model.Bilhete;
import com.vendabilhetes.model.repository.BilheteRepository;

@Service
public class BilheteService {

    @Autowired
    private BilheteRepository bilheteRepository;

    public List<Bilhete> listarTodos() {
        return bilheteRepository.findAll();
    }

    public Bilhete buscarPorId(Long id) {
        return bilheteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bilhete não encontrado com id: " + id));
    }

    public Optional<Bilhete> buscarPorCodigo(String codigo) {
        return bilheteRepository.findByCodigoBilhete(codigo);
    }

    public List<Bilhete> buscarPorUsuario(Integer usuarioId) {
        return bilheteRepository.findByUsuarioIdUsuario(usuarioId);
    }

    public Bilhete salvar(Bilhete bilhete) {
        return bilheteRepository.save(bilhete);
    }

    public void deletar(Integer id) {
        bilheteRepository.deleteById(id.longValue());
    }

    public long countHoje() {
        LocalDateTime hoje = LocalDateTime.now();
        return bilheteRepository.countByDataEmissaoBetween(hoje, hoje.plusDays(1));
    }

    public long countPendentes() {
        return bilheteRepository.countByStatus(Bilhete.Status.EMITIDO);
    }

    public long countVendidosPorPromotor() {
        return bilheteRepository.countVendidosPorPromotor();
    }

    /*
     * Receita total de bilhetes vendidos por um dado promotor.
     * @param promotorId o ID do usuário-promotor
     * @return soma do preço dos bilhetes; retorna 0.0 se for null
     */
    public Double getReceitaPorPromotor(Integer promotorId) {
        return bilheteRepository.sumReceitaByPromotor(promotorId);
    }


}
