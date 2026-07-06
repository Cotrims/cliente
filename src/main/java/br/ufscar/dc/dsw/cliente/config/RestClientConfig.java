package br.ufscar.dc.dsw.cliente.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configura o RestClient (cliente HTTP do Spring) usado para consumir a
 * API REST do servidor Imobiliaria. A URL base vem de application.properties
 * (propriedade api.base-url).
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient(@Value("${api.base-url}") String baseUrl) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
