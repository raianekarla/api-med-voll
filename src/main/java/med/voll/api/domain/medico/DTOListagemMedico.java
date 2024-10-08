package med.voll.api.domain.medico;

public record DTOListagemMedico(Long id, String nome, String email, String crm, Especialidade especialidade) {

    public DTOListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}