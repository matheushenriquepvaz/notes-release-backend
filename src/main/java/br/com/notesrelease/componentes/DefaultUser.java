package br.com.notesrelease.componentes;

/**
 * Created by DEINF.MHVAZ on 27/03/2019.
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class DefaultUser extends User {

    private static final long serialVersionUID = 1L;

    private String nome;

    public DefaultUser(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);

        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
