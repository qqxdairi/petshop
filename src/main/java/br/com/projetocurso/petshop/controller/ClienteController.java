package br.com.projetocurso.petshop.controller;

import br.com.projetocurso.petshop.dto.ClienteDto;
import br.com.projetocurso.petshop.mapper.ClienteMapper;
import br.com.projetocurso.petshop.model.Animal;
import br.com.projetocurso.petshop.model.Cliente;
import br.com.projetocurso.petshop.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDto> incluir(@RequestBody Cliente cliente) {
        ClienteDto clienteIncluido = clienteMapper.toDto(clienteService.salvar(cliente));
        URI localizacao = URI.create("cliente/" + clienteIncluido.getId());
        return ResponseEntity.created(localizacao).body(clienteIncluido);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDto> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        ClienteDto clienteAtualizado = clienteMapper.toDto(clienteService.salvar(cliente));
        return ResponseEntity.ok().body(clienteAtualizado);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDto> recuperarPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(clienteMapper.toDto(clienteService.recuperarPorId(id)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteDto>> recuperarTodos() {
        return ResponseEntity.ok().body(clienteMapper.toCollectionDto(clienteService.recuperarTodos()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        clienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}