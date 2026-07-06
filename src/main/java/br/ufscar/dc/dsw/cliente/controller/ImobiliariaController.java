package br.ufscar.dc.dsw.cliente.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.cliente.model.Imobiliaria;
import br.ufscar.dc.dsw.cliente.service.ImobiliariaService;

/**
 * Controlador Spring MVC das telas (Thymeleaf) de CRUD de imobiliarias.
 * Delega todo o acesso a dados ao ImobiliariaService, que fala com a API
 * REST do servidor via RestClient.
 */
@Controller
@RequestMapping("/imobiliarias")
public class ImobiliariaController {

    private final ImobiliariaService service;

    public ImobiliariaController(ImobiliariaService service) {
        this.service = service;
    }

    /** Lista todas as imobiliarias. */
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("imobiliarias", service.listar());
        return "imobiliarias/lista";
    }

    /** Exibe o formulario de cadastro (nova imobiliaria). */
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("imobiliaria", new Imobiliaria());
        model.addAttribute("modo", "novo");
        return "imobiliarias/form";
    }

    /** Processa o cadastro (POST na API). */
    @PostMapping
    public String criar(@ModelAttribute("imobiliaria") Imobiliaria imobiliaria,
                        RedirectAttributes ra) {
        try {
            service.criar(imobiliaria);
            ra.addFlashAttribute("sucesso", "Imobiliaria cadastrada com sucesso.");
        } catch (RestClientResponseException e) {
            ra.addFlashAttribute("erro", "Erro ao cadastrar: " + mensagem(e));
        }
        return "redirect:/imobiliarias";
    }

    /** Detalhe de uma imobiliaria. */
    @GetMapping("/{id}")
    public String detalhar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("imobiliaria", service.buscarPorId(id));
            return "imobiliarias/detalhe";
        } catch (RestClientResponseException e) {
            ra.addFlashAttribute("erro", "Imobiliaria nao encontrada.");
            return "redirect:/imobiliarias";
        }
    }

    /** Exibe o formulario de edicao preenchido. */
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            Imobiliaria imobiliaria = service.buscarPorId(id);
            imobiliaria.setPassword(null); // nunca reexibimos a senha
            model.addAttribute("imobiliaria", imobiliaria);
            model.addAttribute("modo", "editar");
            return "imobiliarias/form";
        } catch (RestClientResponseException e) {
            ra.addFlashAttribute("erro", "Imobiliaria nao encontrada.");
            return "redirect:/imobiliarias";
        }
    }

    /** Processa a edicao (PUT na API). */
    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @ModelAttribute("imobiliaria") Imobiliaria imobiliaria,
                            RedirectAttributes ra) {
        try {
            service.atualizar(id, imobiliaria);
            ra.addFlashAttribute("sucesso", "Imobiliaria atualizada com sucesso.");
        } catch (RestClientResponseException e) {
            ra.addFlashAttribute("erro", "Erro ao atualizar: " + mensagem(e));
        }
        return "redirect:/imobiliarias";
    }

    /** Remove uma imobiliaria (DELETE na API). */
    @PostMapping("/{id}/remover")
    public String remover(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.remover(id);
            ra.addFlashAttribute("sucesso", "Imobiliaria removida com sucesso.");
        } catch (RestClientResponseException e) {
            ra.addFlashAttribute("erro", "Erro ao remover: " + mensagem(e));
        }
        return "redirect:/imobiliarias";
    }

    private String mensagem(RestClientResponseException e) {
        String corpo = e.getResponseBodyAsString();
        return (corpo == null || corpo.isBlank()) ? e.getStatusText() : corpo;
    }
}
