package br.com.projetocurso.petshop.mapper;

import br.com.projetocurso.petshop.dto.UsuarioDto;
import br.com.projetocurso.petshop.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toEntity(UsuarioDto usuarioDto) {
        return modelMapper.map(usuarioDto, Usuario.class);
    }

    public UsuarioDto toDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }
}
