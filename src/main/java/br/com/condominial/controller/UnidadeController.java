package br.com.condominial.controller;

import br.com.condominial.domain.Unidade;
import br.com.condominial.enums.SituacaoUnidade;
import br.com.condominial.service.BusinessException;
import br.com.condominial.service.UnidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("unidades", service.listarTodas());
        return "unidades/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("unidade", new Unidade());
        model.addAttribute("situacoes", SituacaoUnidade.values());
        return "unidades/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Unidade unidade, BindingResult result,
                       Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("situacoes", SituacaoUnidade.values());
            return "unidades/form";
        }
        try {
            service.salvar(unidade);
            ra.addFlashAttribute("successMessage", "Unidade salva com sucesso!");
            return "redirect:/unidades";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("situacoes", SituacaoUnidade.values());
            return "unidades/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("unidade", service.buscarPorId(id));
        model.addAttribute("situacoes", SituacaoUnidade.values());
        return "unidades/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Unidade unidade,
                         BindingResult result, Model model, RedirectAttributes ra) {
        unidade.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("situacoes", SituacaoUnidade.values());
            return "unidades/form";
        }
        try {
            service.salvar(unidade);
            ra.addFlashAttribute("successMessage", "Unidade atualizada com sucesso!");
            return "redirect:/unidades";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("situacoes", SituacaoUnidade.values());
            return "unidades/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("successMessage", "Unidade excluída com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erro ao excluir unidade: " + e.getMessage());
        }
        return "redirect:/unidades";
    }
}
