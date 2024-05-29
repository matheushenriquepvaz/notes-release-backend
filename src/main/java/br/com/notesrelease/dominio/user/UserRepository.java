package br.com.notesrelease.dominio.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DEINF.MHVAZ on 15/03/2019.
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByHashTrocaDeSenha(String hash);

}
