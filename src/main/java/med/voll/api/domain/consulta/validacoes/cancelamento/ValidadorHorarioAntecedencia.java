package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DTOCancelarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("ValidadorHorarioAntecedenciaCancelamento")
public class ValidadorHorarioAntecedencia implements ValidadorCancelamentoConsulta{
    @Autowired
    private ConsultaRepository repository;

    @Override
    public void validar(DTOCancelarConsulta dados) {
        var consulta = repository.getReferenceById(dados.idConsulta());
        var agora = LocalDateTime.now();
        var diferencaHoras = Duration.between(agora, consulta.getData()).toHours();

        if (diferencaHoras < 24) {
            throw new ValidacaoException("Consulta deve ser Cancelada com antecedência mínima de 24 horass");
        }
    }
}
