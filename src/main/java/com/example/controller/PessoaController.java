package com.example.controller;

import com.example.model.Pessoa;
import com.example.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping({"/pessoas"})
public class PessoaController {

    @Autowired
    private PessoaRepository repository;

    @GetMapping
    public List<Pessoa> getAll() {
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity getById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(path = "/findByName/{nome}")
    public ResponseEntity<?> findByNomeIgnoreCaseContainingOrderByNomeAsc(@PathVariable String nome){
        return new ResponseEntity<>(repository.findByNomeIgnoreCaseContainingOrderByNomeAsc(nome), HttpStatus.OK);
    }

    @PostMapping
    public Pessoa create(@RequestBody Pessoa contact) {
        return repository.save(contact);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") Integer id,
                                 @RequestBody Pessoa pessoa) {
        return repository.findById(id)
                .map(p -> {
                    p.setNome(pessoa.getNome());
                    p.setSexo(pessoa.getSexo());
                    p.setEmail(pessoa.getEmail());
                    p.setDataNascimento(pessoa.getDataNascimento());
                    p.setNaturalidade(pessoa.getNaturalidade());
                    p.setNacionalidade(pessoa.getNacionalidade());
                    p.setCpf(pessoa.getCpf());
                    return ResponseEntity.ok().body(p);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/deleteAll")
    public ResponseEntity<?> deleteAll(){
        repository.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
