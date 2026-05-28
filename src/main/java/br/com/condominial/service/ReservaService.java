package br.com.condominial.service;

import br.com.condominial.domain.ReservaAreaComum;
import br.com.condominial.enums.StatusReserva;
import br.com.condominial.repository.ReservaAreaComumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaAreaComumRepository repository;

    public List<ReservaAreaComum> listarTodas() {
        return repository.findAll();
    }

    public ReservaAreaComum buscarPorId(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new BusinessException("Reserva não encontrada: " + id));
    }

    @Transactional
    public ReservaAreaComum salvar(ReservaAreaComum reserva) {
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
        buscarPorId(id);
        repository.deleteById(id);
    }
}
