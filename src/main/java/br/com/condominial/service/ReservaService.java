package br.com.condominial.service;

import br.com.condominial.domain.ReservaAreaComum;
import br.com.condominial.enums.StatusReserva;
import br.com.condominial.repository.ReservaAreaComumRepository;
import br.com.condominial.security.AccessControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaAreaComumRepository repository;
    private final AccessControlService accessControl;

    public List<ReservaAreaComum> listarTodas() {
        if (accessControl.isAdmin()) {
            return repository.findAll();
        }
        return repository.findByUnidadeId(accessControl.getUnidadeId());
    }

    public ReservaAreaComum buscarPorId(Long id) {
        ReservaAreaComum reserva = repository.findById(id)
            .orElseThrow(() -> new BusinessException("Reserva não encontrada: " + id));
        accessControl.verificarAcesso(reserva.getUnidade().getId());
        return reserva;
    }

    @Transactional
    public ReservaAreaComum salvar(ReservaAreaComum reserva) {
        accessControl.verificarAcesso(reserva.getUnidade().getId());
        if (reserva.getFim() == null || reserva.getInicio() == null) {
            throw new BusinessException("Data/hora de início e fim são obrigatórios");
        }
        if (!reserva.getFim().isAfter(reserva.getInicio())) {
            throw new BusinessException("A data/hora de fim deve ser posterior ao início");
        }
        if (reserva.getSolicitante() != null && reserva.getUnidade() != null) {
            if (!reserva.getSolicitante().getUnidade().getId().equals(reserva.getUnidade().getId())) {
                throw new BusinessException("O solicitante deve ser morador da unidade informada");
            }
        }
        Long excludeId = reserva.getId() != null ? reserva.getId() : -1L;
        List<ReservaAreaComum> conflitos = repository.findConflitos(
            reserva.getArea(), StatusReserva.APROVADA,
            reserva.getInicio(), reserva.getFim(), excludeId
        );
        if (!conflitos.isEmpty()) {
            throw new BusinessException("Já existe uma reserva aprovada para esta área no período informado");
        }
        return repository.save(reserva);
    }

    @Transactional
    public void excluir(Long id) {
        ReservaAreaComum reserva = buscarPorId(id); // verificarAcesso chamado dentro de buscarPorId
        repository.deleteById(reserva.getId());
    }
}
