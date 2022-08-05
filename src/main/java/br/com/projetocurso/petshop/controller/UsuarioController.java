package br.com.projetocurso.petshop.controller;

import br.com.projetocurso.petshop.dto.LoginDto;
import br.com.projetocurso.petshop.dto.TokenDto;
import br.com.projetocurso.petshop.dto.UsuarioDto;
import br.com.projetocurso.petshop.mapper.UsuarioMapper;
import br.com.projetocurso.petshop.service.TokenService;
import br.com.projetocurso.petshop.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDto> incluir(@RequestBody UsuarioDto usuarioDto) {
        UsuarioDto usuarioIncluido = usuarioMapper.toDto(usuarioService.salvar(usuarioMapper.toEntity(usuarioDto)));
        URI localizacao = URI.create("usuario/" + usuarioIncluido.getId());
        return ResponseEntity.created(localizacao).body(usuarioIncluido);
    }

    @PostMapping("/logar")
    public ResponseEntity<?> criarAcesso(@RequestBody LoginDto loginDto) {
        try {
            String hash = usuarioService.criptografarSenha(loginDto.getSenha());
            loginDto.setSenha(hash);

            UsernamePasswordAuthenticationToken dadosLogin = loginDto.converter();
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.ok(new TokenDto("Bearer", token));
        } catch (AuthenticationException e) {
            if (e.getMessage().equals("Bad credentials")) {
                return ResponseEntity.badRequest().body("Usuário e/ou senha incorretos.");
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}