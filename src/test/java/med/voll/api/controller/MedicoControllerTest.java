package med.voll.api.controller;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MedicoRepository repository;

    @Autowired
    private JacksonTester<DTOCadastroMedico> dadosCadastroJson;

    @Autowired
    private JacksonTester<DTODetalhamentoMedico> dadosDetalhamentoJson;


    @Test
    @DisplayName("Deveria devolver codigo 400 quando informacoes estao invalidas")
    @WithMockUser
    void cadatrar_cenario01() throws Exception {
        var response = mvc.perform(post("/medicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Deveria devolver codigo 200 quando informacoes estao validas")
    @WithMockUser
    void cadatrar_cenario02() throws Exception {
        var dadosCadastro = new DTOCadastroMedico(
                "medico",
                "medico@medvoll.com",
                "99999999",
                "568439",
                Especialidade.CADIOLOGIA,
                dadosEndereco()
        );

        var dadosDetalhamento = new DTODetalhamentoMedico(
                null,
                dadosCadastro.nome(),
                dadosCadastro.email(),
                dadosCadastro.crm(),
                dadosCadastro.telefone(),
                dadosCadastro.especialidade(),
                new Endereco(dadosCadastro.endereco())
        );

        when(repository.save(any())).thenReturn(new Medico(dadosCadastro));

        var response = mvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroJson.write(dadosCadastro).getJson()))
                .andReturn().getResponse();



        var jsonEsperado = dadosDetalhamentoJson.write(dadosDetalhamento).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);

    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua",
                "bairro",
                "00000000",
                "Rio grande do norte",
                "RN",
                null,
                null
        );
    }
}