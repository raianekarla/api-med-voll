package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.consulta.DTOAgendamentoConsulta;

public interface ValidadorAgendamentoConsulta {
    void validar(DTOAgendamentoConsulta dados);
}
