package br.com.projetocurso.petshop.service;

import br.com.projetocurso.petshop.model.Usuario;
import br.com.projetocurso.petshop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }

    public Usuario recuperarPorId(Long id) throws Exception {
        return usuarioRepository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public Usuario salvar(Usuario usuario) {
        try {
            String hash = criptografarSenha(usuario.getSenha());
            usuario.setSenha(hash);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return usuarioRepository.save(usuario);
    }

    public String criptografarSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
        byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : messageDigest) {
            stringBuilder.append(String.format("%02X", 0xFF & b));
        }

        return stringBuilder.toString();
    }
}