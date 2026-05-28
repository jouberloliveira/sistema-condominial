package br.com.condominial.controller;

import br.com.condominial.domain.Morador;
import br.com.condominial.enums.SimNao;
import br.com.condominial.enums.TipoMorador;
import br.com.condominial.service.BusinessException;
import br.com.condominial.service.MoradorService;
import br.com.condominial.service.UnidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/moradores")
@RequiredArgsConstructor
public class MoradorController {

    private final MoradorService service;
    private final UnidadeService unidadeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("moradores", service.listarTodos());
        return "moradores/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("morador", new Morador());
        model.addAttribute("unidades", unidadeService.listarTodas());
        model.addAttribute("tipos", TipoMorador.values());
        model.addAttribute("simNao", SimNao.values());
        return "moradores/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Morador morador, BindingResult result,
                       Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("unidades", unidadeService.listarTodas());
            model.addAttribute("tipos", TipoMorador.values());
            model.addAttribute("simNao", SimNao.values());
            return "moradores/form";
        }
        try {
            service.salvar(morador);
            ra.addFlashAttribute("successMessage", "Morador salvo com sucesso!");
            return "redirect:/moradores";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("unidades", unidadeService.listarTodas());
            model.addAttribute("tipos", TipoMorador.values());
            model.addAttribute("simNao", SimNao.values());
            return "moradores/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("morador", service.buscarPorId(id));
        model.addAttribute("unidades", unidadeService.listarTodas());
        model.addAttribute("tipos", TipoMorador.values());
        model.addAttribute("simNao", SimNao.values());
        return "moradores/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Morador morador,
                         BindingResult result, Model model, RedirectAttributes ra) {
        morador.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("unidades", unidadeService.listarTodas());
            model.addAttribute("tipos", TipoMorador.values());
            model.addAttribute("simNao", SimNao.values());
            return "moradores/form";
        }
        try {
            service.salvar(morador);
            ra.addFlashAttribute("successMessage", "Morador atualizado com sucesso!");
            return "redirect:/moradores";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("unidades", unidadeService.listarTodas());
            model.addAttribute("tipos", TipoMorador.values());
            model.addAttribute("simNao", SimNao.values());
            return "moradores/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("successMessage", "Morador excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erro ao excluir morador: " + e.getMessage());
        }
        return "redirect:/moradores";
    }
}
