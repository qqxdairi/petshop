package br.com.projetocurso.petshop.service;

import br.com.projetocurso.petshop.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${petshop.jwt.expiracao}")
    private String expiracao;

    @Value("${petshop.jwt.segredo}")
    private String segredo;

    public String gerarToken(Authentication authentication) {
        Date dataCriacao = new Date();
        Date dataExpiracao = new Date(dataCriacao.getTime() + Long.parseLong(expiracao));

        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();

        return Jwts.builder().setIssuer("petshop")
                .setId(usuarioLogado.getId().toString())
                .setSubject(usuarioLogado.getNome())
                .setIssuedAt(dataCriacao)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, segredo)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(segredo).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUsuarioId(String token) {
        Claims claims = Jwts.parser().setSigningKey(segredo).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getId());
    }
}