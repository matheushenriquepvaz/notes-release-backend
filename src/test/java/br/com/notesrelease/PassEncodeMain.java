package br.com.notesrelease;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
public class PassEncodeMain {


    public static void main(String[] args) {
        String senha = "123456";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String novaSenha = encoder.encode(senha);
        log.info(novaSenha);
    }

}
