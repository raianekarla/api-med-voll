package med.voll.api.domain.consulta.validacoes.cancelamento;

import med.voll.api.domain.consulta.DTOCancelarConsulta;

public interface ValidadorCancelamentoConsulta {
    void validar(DTOCancelarConsulta dados);
}
