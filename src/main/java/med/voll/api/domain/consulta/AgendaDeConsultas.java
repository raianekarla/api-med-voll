package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoConsulta;
import med.voll.api.domain.consulta.validacoes.cancelamento.ValidadorCancelamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;

    @Autowired
    private List<ValidadorCancelamentoConsulta> validadorCancelamento;

    public DTODetalhamentoConsulta agendar(DTOAgendamentoConsulta dados){
        if(!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id paciente informado não existe");
        }
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())){
            throw new ValidacaoException("Id medico informado não existe");
        }

        validadores.forEach(v -> v.validar(dados)); //passando por todos os validadores (regras de negocio)

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if (medico == null) {
            throw new ValidacaoException("Não existi médico disponivel nesta data");
        }

        var consulta = new Consulta(null, medico, paciente, dados.data(),null);
        consultaRepository.save(consulta);

        return new DTODetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DTOAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
          return medicoRepository.getReferenceById(dados.idMedico());
        }
        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatoria quando o médico não for escolhido");
        }
        return medicoRepository.escolherMedicoAleatorioLivreData(dados.especialidade(), dados.data());
    }

    public void cancelar(DTOCancelarConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe");
        }

        validadorCancelamento.forEach(v -> v.validar(dados));

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
