package br.com.notesrelease.services.login;


import br.com.notesrelease.filters.JWTLoginFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.io.IOException;

import static br.com.notesrelease.Util.Util.API_ROUTE;

@Service
public class LoginService {

    AuthenticationManager authenticationManager;

    JWTLoginFilter loginFilter;

    public LoginService(AuthenticationManager authenticationManager) {
        loginFilter = new JWTLoginFilter(API_ROUTE + "login", authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    public String login(String username, String password) throws ServletException, IOException {
        return loginFilter.autenticar(username, password);
    }

}
