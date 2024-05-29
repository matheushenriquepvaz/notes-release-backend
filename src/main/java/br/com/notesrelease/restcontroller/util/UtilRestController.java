package br.com.notesrelease.restcontroller.util;

import br.com.notesrelease.Util.Util;
import br.com.notesrelease.dtos.UserDTO;
import br.com.notesrelease.infra.exceptions.ApiError;
import br.com.notesrelease.services.user.UserService;
import br.com.notesrelease.services.util.UtilClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import static br.com.notesrelease.Util.Util.*;
import static br.com.notesrelease.Util.Util.BARRA;

@RestController
@Component
@RequestMapping(value = BARRA + API + BARRA + COMUM + BARRA + "util" , produces = MediaType.APPLICATION_JSON_VALUE)
public class UtilRestController {

    private UtilClassService utilClassService;

    public UtilRestController(UtilClassService utilClassService) {
        this.utilClassService = utilClassService;
    }

    @GetMapping("/normalize")
    public ResponseEntity<String> normalize (@RequestParam("text") String text) {
        return ResponseEntity.ok().body(utilClassService.returnNormalizedString(text));
    }

}
