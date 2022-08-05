package br.com.projetocurso.petshop.common;

import br.com.projetocurso.petshop.model.Usuario;
import br.com.projetocurso.petshop.service.TokenService;
import br.com.projetocurso.petshop.service.UsuarioService;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    private TokenService tokenService;
    private UsuarioService usuarioService;

    public JwtAuthorizationFilter(TokenService tokenService, UsuarioService usuarioService) {
        this.tokenService = tokenService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        if (tokenService.isTokenValid(token)) {
            try {
                autenticaUsuario(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String jwtToken = request.getHeader(HEADER);

        if (jwtToken != null && !jwtToken.isEmpty() && jwtToken.startsWith(PREFIX)) {
            return jwtToken.replace(PREFIX, "");
        }

        return null;
    }

    private void autenticaUsuario(String token) throws Exception {
        Long usuarioId = tokenService.getUsuarioId(token);
        Usuario usuario = usuarioService.recuperarPorId(usuarioId);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}