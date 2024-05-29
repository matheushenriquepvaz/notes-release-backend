package br.com.notesrelease.componentes;

import br.com.notesrelease.dominio.user.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by DEINF.MHVAZ on 27/03/2019.
 */
@Component
public class ComercialUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    private GroupRepository groupRepository;

    private PermissionRepository permissionRepository;

    public ComercialUserDetailsService(UserRepository userRepository, GroupRepository groupRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null || !user.isActive()) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        return new DefaultUser(user.getEmail(), user.getPassword(), authorities(user));
    }

    public Collection<? extends GrantedAuthority> authorities(User user) {
        return authorities(groupRepository.findByUsers(user), user);
    }

    public Collection<? extends GrantedAuthority> authorities(List<Group> groups, User user) {
        Collection<GrantedAuthority> auths = new ArrayList<>();

        for (Group group : groups) {
            //List<Permissao> lista = permissaoRepository.findByGruposIn(grupo);
            for (Permission permission : group.getPermissoes()) {
                auths.add(new SimpleGrantedAuthority("ROLE_" + permission.getIdentificador()));
            }
        }

        //List<Permissao> permissoes = permissaoRepository.findByUsuariosIn(usuario);
        List<Permission> permissoes = user.getPermissoes();
        for (Permission permission : permissoes) {
            //List<Permissao> lista = permissaoRepository.findByGruposIn(grupo);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + permission.getIdentificador());
            if (!auths.contains(authority)) {
                auths.add(authority);
            }
        }

        return auths;
    }
}