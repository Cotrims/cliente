package br.ufscar.dc.dsw.cliente.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO que representa uma Imobiliaria trafegada em JSON pela API REST do
 * servidor Imobiliaria (/api/imobiliarias).
 *
 * Campos aceitos pela API: nome, email, password, CNPJ, descricao.
 * (a role e definida automaticamente pelo servidor no POST)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Imobiliaria {

    private Long id;
    private String nome;
    private String email;
    private String password;

    // No JSON da API o campo e "CNPJ" (maiusculo)
    @JsonProperty("CNPJ")
    private String cnpj;

    private String descricao;
    private String role;

    public Imobiliaria() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
