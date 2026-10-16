package br.edu.fiap.banco.service;

import br.edu.fiap.banco.dto.LoginRequest;
import br.edu.fiap.banco.dto.TokenResponse;
import br.edu.fiap.banco.entity.Usuario;
import br.edu.fiap.banco.repository.UsuarioRepository;
import br.edu.fiap.banco.security.JwtService;
import br.edu.fiap.banco.security.SenhaBCrypt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class AutenticacaoService {
    private final UsuarioRepository usuarios;
    private final SenhaBCrypt senhas;
    private final JwtService token;

    @Inject
    public AutenticacaoService(
            UsuarioRepository usuarios,
            SenhaBCrypt senhas,
            JwtService token
    ){
        this.usuarios = usuarios;
        this.senhas = senhas;
        this.token = token;
    }

    public TokenResponse login(LoginRequest request){
        Usuario usuario = usuarios.buscaPorEmail(request.email())
                .orElseThrow(() -> new NotAuthorizedException("Bearer"));

        if(!usuario.isAtivo() || !senhas.confere(request.senha(), usuario.getSenha())){
            throw new NotAuthorizedException("Bearer");
        }

        return token.emitir(usuario);
    }
}
