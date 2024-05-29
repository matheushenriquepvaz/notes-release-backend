package br.com.notesrelease.services.permission;

import br.com.notesrelease.dominio.user.Permission;
import br.com.notesrelease.dominio.user.PermissionRepository;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    private PermissionRepository repository;

    public PermissaoService(PermissionRepository repository) {
        this.repository = repository;
    }

    public Permission retornaPermissaoPorIdentificador(String identificador) {
        return repository.findByIdentificador(identificador);
    }

    public Permission retornaPorId(Long id) {
        return this.repository.findById(id).orElse(null);
    }

}
