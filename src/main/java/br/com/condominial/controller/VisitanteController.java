package br.com.condominial.controller;

import br.com.condominial.domain.Visitante;
import br.com.condominial.enums.SimNao;
import br.com.condominial.enums.TipoVisitante;
import br.com.condominial.service.BusinessException;
import br.com.condominial.service.MoradorService;
import br.com.condominial.service.UnidadeService;
import br.com.condominial.service.VisitanteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/visitantes")
@RequiredArgsConstructor
public class VisitanteController {

    private final VisitanteService service;
    private final UnidadeService unidadeService;
    private final MoradorService moradorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("visitantes", service.listarTodos());
        return "visitantes/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("visitante", new Visitante());
        model.addAttribute("unidades", unidadeService.listarTodas());
        model.addAttribute("moradores", moradorService.listarTodos());
        model.addAttribute("tipos", TipoVisitante.values());
        model.addAttribute("simNao", SimNao.values());
        return "visitantes/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Visitante visitante, BindingResult result,
                       Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("unidades", unidadeService.listarTodas());
            model.addAttribute("moradores", moradorService.listarTodos());
            model.addAttribute("tipos", TipoVisitante.values());
            model.addAttribute("simNao", SimNao.values());
            return "visitantes/form";
        }
        try {
            service.salvar(visitante);
            ra.addFlashAttribute("successMessage", "Visitante salvo com sucesso!");
            return "redirect:/visitantes";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("unidades", unidadeService.listarTodas());
            model.addAttribute("moradores", moradorService.listarTodos());
            model.addAttribute("tipos", TipoVisitante.values());
            model.addAttribute("simNao", SimNao.values());
            return "visitantes/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("visitante", service.buscarPorId(id));
        model.addAttribute("unidades", unidadeService.listarTodas());
        model.addAttribute("moradores", moradorService.listarTodos());
        model.addAttribute("tipos", TipoVisitante.values());
        model.addAttribute("simNao", SimNao.values());
        return "visitantes/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Visitante visitante,
                         BindingResult result, Model model, RedirectAttributes ra) {
        visitante.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("unidades", unidadeService.listarTodas());
            model.addAttribute("moradores", moradorService.listarTodos());
            model.addAttribute("tipos", TipoVisitante.values());
            model.addAttribute("simNao", SimNao.values());
            return "visitantes/form";
        }
        try {
            service.salvar(visitante);
            ra.addFlashAttribute("successMessage", "Visitante atualizado com sucesso!");
            return "redirect:/visitantes";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("unidades", unidadeService.listarTodas());
            model.addAttribute("moradores", moradorService.listarTodos());
            model.addAttribute("tipos", TipoVisitante.values());
            model.addAttribute("simNao", SimNao.values());
            return "visitantes/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("successMessage", "Visitante excluído com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erro ao excluir visitante: " + e.getMessage());
        }
        return "redirect:/visitantes";
    }
}
