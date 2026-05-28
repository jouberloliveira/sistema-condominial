package br.com.condominial.controller;

import br.com.condominial.domain.Ocorrencia;
import br.com.condominial.enums.PrioridadeOcorrencia;
import br.com.condominial.enums.StatusOcorrencia;
import br.com.condominial.enums.TipoOcorrencia;
import br.com.condominial.service.BusinessException;
import br.com.condominial.service.MoradorService;
import br.com.condominial.service.OcorrenciaService;
import br.com.condominial.service.UnidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ocorrencias")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;
    private final UnidadeService unidadeService;
    private final MoradorService moradorService;

    private void addFormData(Model model) {
        model.addAttribute("unidades", unidadeService.listarTodas());
        model.addAttribute("moradores", moradorService.listarTodos());
        model.addAttribute("tipos", TipoOcorrencia.values());
        model.addAttribute("prioridades", PrioridadeOcorrencia.values());
        model.addAttribute("statusList", StatusOcorrencia.values());
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ocorrencias", service.listarTodas());
        return "ocorrencias/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("ocorrencia", new Ocorrencia());
        addFormData(model);
        return "ocorrencias/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Ocorrencia ocorrencia, BindingResult result,
                       Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            addFormData(model);
            return "ocorrencias/form";
        }
        try {
            service.salvar(ocorrencia);
            ra.addFlashAttribute("successMessage", "Ocorrência salva com sucesso!");
            return "redirect:/ocorrencias";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            addFormData(model);
            return "ocorrencias/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("ocorrencia", service.buscarPorId(id));
        addFormData(model);
        return "ocorrencias/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute Ocorrencia ocorrencia,
                         BindingResult result, Model model, RedirectAttributes ra) {
        ocorrencia.setId(id);
        if (result.hasErrors()) {
            addFormData(model);
            return "ocorrencias/form";
        }
        try {
            service.salvar(ocorrencia);
            ra.addFlashAttribute("successMessage", "Ocorrência atualizada com sucesso!");
            return "redirect:/ocorrencias";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            addFormData(model);
            return "ocorrencias/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("successMessage", "Ocorrência excluída com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erro ao excluir ocorrência: " + e.getMessage());
        }
        return "redirect:/ocorrencias";
    }
}
