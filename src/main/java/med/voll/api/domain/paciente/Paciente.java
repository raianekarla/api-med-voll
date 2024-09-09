package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private Boolean ativo;

    @Embedded
    private Endereco endereco;


    public Paciente(DTOPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DTOAtualizacaoPaciente paciente) {
        if (paciente.nome() != null) {
            this.nome = paciente.nome();
        }
        if (paciente.telefone() != null) {
            this.telefone = paciente.telefone();
        }
        if (paciente.endereco() != null) {
            this.endereco.atualizarInformacoes(paciente.endereco());
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
