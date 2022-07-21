package br.com.projetocurso.petshop.mapper;

import br.com.projetocurso.petshop.dto.AnimalDto;
import br.com.projetocurso.petshop.dto.ClienteDto;
import br.com.projetocurso.petshop.model.Animal;
import br.com.projetocurso.petshop.model.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cliente toEntity(ClienteDto clienteDto) {
        return modelMapper.map(clienteDto, Cliente.class);
    }

    public ClienteDto toDto(Cliente cliente) {
        return modelMapper.map(cliente, ClienteDto.class);
    }

    public List<ClienteDto> toCollectionDto(List<Cliente> clientes) {
//        List<ClienteDto> clienteDtos = new ArrayList<>();
//
//        for (Cliente cliente : clientes) {
//            clienteDtos.add(toDto(cliente));
//        }
//
//        return clienteDtos;

        return clientes.stream().map(this::toDto).collect(Collectors.toList());
    }
}