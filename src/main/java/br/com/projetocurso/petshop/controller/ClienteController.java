package br.com.projetocurso.petshop.controller;

import br.com.projetocurso.petshop.model.Cliente;
import br.com.projetocurso.petshop.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(value = "cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> incluir(@RequestBody Cliente cliente) {
        Cliente clienteIncluido = clienteService.salvar(cliente);
        URI localizacao = URI.create("cliente/" + clienteIncluido.getId());
        return ResponseEntity.created(localizacao).body(clienteIncluido);
    }
}
