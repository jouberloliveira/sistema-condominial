package br.com.condominial.controller;

import br.com.condominial.domain.ReservaAreaComum;
import br.com.condominial.enums.AreaComum;
import br.com.condominial.enums.StatusReserva;
import br.com.condominial.service.BusinessException;
import br.com.condominial.service.MoradorService;
import br.com.condominial.service.ReservaService;
import br.com.condominial.service.UnidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;
    private final UnidadeService unidadeService;
    private final MoradorService moradorService;

    private void addFormData(Model model) {
        model.addAttribute("unidades", unidadeService.listarTodas());
        model.addAttribute("moradores", moradorService.listarTodos());
        model.addAttribute("areas", AreaComum.values());
        model.addAttribute("statusList", StatusReserva.values());
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("reservas", service.listarTodas());
        return "reservas/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("reserva", new ReservaAreaComum());
        addFormData(model);
        return "reservas/form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute ReservaAreaComum reserva, BindingResult result,
                       Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            addFormData(model);
            return "reservas/form";
        }
        try {
            service.salvar(reserva);
            ra.addFlashAttribute("successMessage", "Reserva salva com sucesso!");
            return "redirect:/reservas";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            addFormData(model);
            return "reservas/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", service.buscarPorId(id));
        addFormData(model);
        return "reservas/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute ReservaAreaComum reserva,
                         BindingResult result, Model model, RedirectAttributes ra) {
        reserva.setId(id);
        if (result.hasErrors()) {
            addFormData(model);
            return "reservas/form";
        }
        try {
            service.salvar(reserva);
            ra.addFlashAttribute("successMessage", "Reserva atualizada com sucesso!");
            return "redirect:/reservas";
        } catch (BusinessException e) {
            model.addAttribute("errorMessage", e.getMessage());
            addFormData(model);
            return "reservas/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("successMessage", "Reserva excluída com sucesso!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMessage", "Erro ao excluir reserva: " + e.getMessage());
        }
        return "redirect:/reservas";
    }
}
