package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired //Injeção de dependência
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public void addMedico(@RequestBody @Valid DTOCadastroMedico dados) {
        repository.save(new Medico(dados));
    }

    @GetMapping("/list") //http://localhost:8080/medicos/list?size=10&page=0&sort=nome,desc
    public Page<DTOListagemMedico> listMedicos(@PageableDefault(size=10, sort = "nome") Pageable paginacao){ //alteracao do default
        return repository.findAllByAtivoTrue(paginacao).map(DTOListagemMedico::new);
    }

    @PutMapping
    @Transactional
    public void updateMedico(@RequestBody @Valid DTOAtulizacaoMedico dados){
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public void deleteMedico(@PathVariable Long id){
//        repository.deleteById(id);
//    }

    //exclusao logica
    @DeleteMapping("/{id}")
    @Transactional
    public void deleteMedico(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
    }
}
