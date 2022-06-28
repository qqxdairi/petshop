package br.com.projetocurso.petshop.service;

import br.com.projetocurso.petshop.model.Cliente;
import br.com.projetocurso.petshop.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteSalvo;
    }
}