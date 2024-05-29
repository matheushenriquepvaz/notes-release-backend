package br.com.notesrelease.restcontroller.login;

import br.com.notesrelease.Util.Util;
import br.com.notesrelease.dominio.user.UserCredentials;
import br.com.notesrelease.services.login.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Authentication API specification for Swagger documentation and Code Generation.
 * Implemented by Spring Security.
 */
@Api("Authentication")
@RestController
@RequestMapping(value = Util.BARRA + Util.API + "/authenticate" , produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthApi {


    private LoginService loginService;

    public AuthApi(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation(value = "Login", notes = "Login with the given credentials.")
    @ApiResponses({@ApiResponse(code = 200, message = "", response = String.class)})
    @PostMapping
    public ResponseEntity<String> login(
            @RequestBody UserCredentials credentials,
            HttpServletRequest request
    ) throws ServletException, IOException {
        if (Util.naoNulo(credentials.getUsername()) && Util.naoNulo(credentials.getPassword())) {
            String response = this.loginService.login(credentials.getUsername(), credentials.getPassword());
            response = "{\"token\": \"" + response + "\"}";
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
        }
    }

    /*
     * Implemented by Spring Security
    @ApiOperation(value = "Logout", notes = "Logout the current user.")
    @ApiResponses({@ApiResponse(code = 200, message = "")})
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout() {
        throw new IllegalStateException("Add Spring Security to handle authentication");
    }
    */
}