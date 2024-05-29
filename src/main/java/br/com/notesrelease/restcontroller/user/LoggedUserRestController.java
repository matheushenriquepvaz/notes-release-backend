package br.com.notesrelease.restcontroller.user;

import br.com.notesrelease.services.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.notesrelease.Util.Util.ROTA_RESTRITO;



@RestController
@Component
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(name = ROTA_RESTRITO + "/usuario-logado")
public class LoggedUserRestController {

    private UserService service;

    public LoggedUserRestController(UserService service) {
        this.service = service;
    }



}
