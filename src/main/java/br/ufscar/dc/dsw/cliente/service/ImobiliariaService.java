package br.ufscar.dc.dsw.cliente.service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import br.ufscar.dc.dsw.cliente.model.Imobiliaria;

/**
 * Camada de servico que consome a API REST /api/imobiliarias do servidor
 * Imobiliaria usando o RestClient. Cobre todas as operacoes CRUD:
 * listar, buscar, criar, atualizar e remover.
 */
@Service
public class ImobiliariaService {

    private static final String RECURSO = "/api/imobiliarias";

    private final RestClient restClient;

    public ImobiliariaService(RestClient restClient) {
        this.restClient = restClient;
    }

    /** GET /api/imobiliarias — lista todas (a API responde 404 quando vazia). */
    public List<Imobiliaria> listar() {
        try {
            Imobiliaria[] resposta = restClient.get()
                    .uri(RECURSO)
                    .retrieve()
                    .body(Imobiliaria[].class);
            return resposta == null ? List.of() : Arrays.asList(resposta);
        } catch (HttpClientErrorException.NotFound e) {
            // Convencao da API: lista vazia retorna 404 (e nao 200 com []).
            return List.of();
        }
    }

    /** GET /api/imobiliarias/{id} — busca uma imobiliaria por id. */
    public Imobiliaria buscarPorId(Long id) {
        return restClient.get()
                .uri(RECURSO + "/{id}", id)
                .retrieve()
                .body(Imobiliaria.class);
    }

    /** POST /api/imobiliarias — cria uma nova imobiliaria (role atribuida pelo servidor). */
    public void criar(Imobiliaria imobiliaria) {
        restClient.post()
                .uri(RECURSO)
                .contentType(MediaType.APPLICATION_JSON)
                .body(imobiliaria)
                .retrieve()
                .toBodilessEntity();
    }

    /**
     * PUT /api/imobiliarias/{id} — atualizacao parcial: envia apenas os campos
     * preenchidos (o password so e enviado quando o usuario informa um novo).
     */
    public void atualizar(Long id, Imobiliaria imobiliaria) {
        Map<String, Object> corpo = new LinkedHashMap<>();
        if (StringUtils.hasText(imobiliaria.getNome())) {
            corpo.put("nome", imobiliaria.getNome());
        }
        if (StringUtils.hasText(imobiliaria.getEmail())) {
            corpo.put("email", imobiliaria.getEmail());
        }
        if (StringUtils.hasText(imobiliaria.getCnpj())) {
            corpo.put("CNPJ", imobiliaria.getCnpj());
        }
        if (StringUtils.hasText(imobiliaria.getDescricao())) {
            corpo.put("descricao", imobiliaria.getDescricao());
        }
        if (StringUtils.hasText(imobiliaria.getPassword())) {
            corpo.put("password", imobiliaria.getPassword());
        }

        restClient.put()
                .uri(RECURSO + "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(corpo)
                .retrieve()
                .toBodilessEntity();
    }

    /** DELETE /api/imobiliarias/{id} — remove a imobiliaria. */
    public void remover(Long id) {
        restClient.delete()
                .uri(RECURSO + "/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
