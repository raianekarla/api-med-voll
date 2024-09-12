package med.voll.api.domain.consulta.validacoes.agendamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DTOAgendamentoConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorHorarioFuncionamento implements ValidadorAgendamentoConsulta {

    public void validar(DTOAgendamentoConsulta dados) {
        var dataConsulta = dados.data();

        var domingo = dataConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDeAbertura = dataConsulta.getHour() < 7;
        var depoisDeEncerramento = dataConsulta.getHour() > 18;

        if (domingo || antesDeAbertura || depoisDeEncerramento) {
            throw  new ValidacaoException("Cunsulta fora do horário de funcionamento da clínica");
        }
    }
}
