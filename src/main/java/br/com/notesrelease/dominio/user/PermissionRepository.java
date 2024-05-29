package br.com.notesrelease.dominio.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by DEINF.MHVAZ on 27/03/2019.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Permission findByIdentificador(String identificador);

    //List<Permissao> findByUsuariosIn(Usuario usuario);

}
