package br.com.notesrelease.dominio.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by DEINF.MHVAZ on 27/03/2019.
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

    List<Group> findByUsers(User user);

}
