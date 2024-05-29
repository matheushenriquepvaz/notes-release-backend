package br.com.notesrelease.dominio.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by DEINF.MHVAZ on 15/03/2019.
 */

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {


}
