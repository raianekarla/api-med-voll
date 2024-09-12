package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;

public record DTOCancelarConsulta(
        @NotNull
        Long idConsulta,

        @NotNull
        MotivoCancelamento motivo
) {
}
