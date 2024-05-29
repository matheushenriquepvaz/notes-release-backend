package br.com.notesrelease.restcontroller.user;


import br.com.notesrelease.dtos.UserDTO;
import br.com.notesrelease.infra.exceptions.ApiError;
import br.com.notesrelease.services.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import static br.com.notesrelease.Util.Util.*;

@RestController
@Component
@CrossOrigin(origins = "http://localhost:3000")
public class UserRestController {

    private static final String COMUM_PERFIL = BARRA + API + BARRA + COMUM + BARRA + PERFIL;
    private static final String ROTA_CONFIRMACAO = COMUM_PERFIL + BARRA + COMFIRMA_CADASTRO + BARRA;
    private static final String ROTA_SOLICITA_EMAIL = COMUM_PERFIL + BARRA + SOLICITA_EMAIL_TROCA_SENHA;
    private static final String ROTA_TROCA_SENHA = COMUM_PERFIL + BARRA + TROCA_SENHA;
    private static final String INFOS_USUARIO = BARRA + API + BARRA + RESTRITO + BARRA + "buscar-usuario";
    private static final String ROTA_EDITAR = BARRA + API + BARRA + RESTRITO + BARRA + PERFIL + BARRA + "editar";
    private static final String ROTA_REENVIO_EMAIL_CONFIRMACAO = COMUM_PERFIL + BARRA + "reenviar-email";


    private UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping(value = COMUM_PERFIL + "/cadastrar")
    public ResponseEntity create(@RequestBody UserDTO usuario) throws Exception {
        try {
            service.create(usuario);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), e.getMessage()));
        }
    }

    @GetMapping(value = INFOS_USUARIO)
    public ResponseEntity<UserDTO> findUser() throws Exception {
        return new ResponseEntity<>(service.buscarUsuario(), HttpStatus.OK);
    }



}
