package br.com.notesrelease.services.user;

import br.com.notesrelease.Util.Util;
import br.com.notesrelease.dominio.user.*;
import br.com.notesrelease.dtos.PhoneDTO;
import br.com.notesrelease.dtos.UserDTO;
import br.com.notesrelease.infra.exceptions.NegocioException;
import br.com.notesrelease.services.permission.PermissaoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Created by DEINF.MHVAZ on 14/03/2019.
 */
@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository repository;

    private PermissaoService permissaoService;

    private TelefoneService telefoneService;

    public UserService(UserRepository repository, PermissaoService permissaoService, TelefoneService telefoneService) {
        this.repository = repository;
        this.permissaoService = permissaoService;
        this.telefoneService = telefoneService;
    }

    @Transactional
    public void testaEmail(String email) throws Exception {
        User user = repository.findByEmail(email);
        if(Util.naoNulo(user) || Util.nulo(email)) {
            //repository.delete(usuario);
            throw new Exception("Email já cadastrado.");
        }
    }

    @Transactional
    public void create(UserDTO usuario) throws Exception {
        if (Util.naoNulo(usuario)) {
            if (validateUser(usuario)) {
                try {
                    prepareUser(usuario);
                } catch (Exception e) {
                    LOGGER.info("Erro ao Cadastrar", e);
                }
            } else {
                throw new Exception("Informações obrigatórias não informadas.");
            }
        } else {
            throw new Exception("Usuário não reconhecido");
        }
    }

    private void prepareUser(UserDTO usuarioDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senha = encoder.encode(usuarioDto.getSenha());
        List<Permission> permissoes = new ArrayList<Permission>();
        Permission permission = permissaoService.retornaPermissaoPorIdentificador(Util.ROLE_USUARIO);
        permissoes.add(permission);
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(usuarioDto, User.class);
        user.setPermissoes(permissoes);
        user.setActive(true);
        user.setBloqueado(false);
        user.setPassword(senha);
        user.setDataCriacao(new Date());
        user.setId(0L);
        User userPersistido = repository.save(user);
    }

    private boolean validateUser(UserDTO usuario) throws Exception {
        testaEmail(usuario.getEmail());
        return !Util.nulosOuEmBranco(usuario.getEmail(), usuario.getNome(), usuario.getSenha(), usuario.getCpf());
    }


    public String activateAccount(String codigo) {
        byte[] decodedBytes = Base64.getDecoder().decode(codigo);
        String decodedString = new String(decodedBytes);
        User user = repository.findByEmail(decodedString);
        if(Util.naoNulo(user) && !user.isActive()) {
            user.setActive(true);
            repository.save(user);
            return "Usuário ativo com sucesso";
        } else {
            return "Usuário não encontrado, ou já ativo.";
        }
    }


    public UserDTO buscarUsuario() {
        User user = repository.findByEmail(Util.recuperaUsuarioLogado());
        ModelMapper mapper = new ModelMapper();
        UserDTO dto = mapper.map(user, UserDTO.class);
        carregaRoles(user, dto);
        dto.setSenha(null);
        return dto;
    }

    private void carregaRoles(User user, UserDTO dto) {
        List<String> roles = new ArrayList<>();
        for (Group group : user.getGroups()) {
            for (Permission permission : group.getPermissoes()) {
                if (!roles.contains(permission.getIdentificador())) {
                    roles.add(permission.getIdentificador());
                }
            }
        }
        for (Permission permission : user.getPermissoes()) {
            if (!roles.contains(permission.getIdentificador())) {
                roles.add(permission.getIdentificador());
            }
        }
        dto.setRoles(roles);
    }


    public User retornaUsuarioLogado() {
        return this.returnUserByID(Util.recuperaUsuarioLogado());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User editar(UserDTO usuarioDto) throws NegocioException {
        //Usuario usuarioTeste = new ModelMapper().map(usuarioDto, Usuario.class);
        User user = repository.findByEmail(Util.recuperaUsuarioLogado());
        //copiarValores(usuarioTeste, usuario);
        copiarValores(usuarioDto, user);
        user = repository.save(user);
        return user;
    }

    public User editar(User user) {
        return this.repository.save(user);
    }

    private void copiarValores(User userRecebido, User userPersistido) {
        userPersistido.setPhones(userRecebido.getPhones());
        if (!Util.emBranco(userRecebido.getName()) && !userRecebido.getName().equals(userPersistido.getName())) {
            userPersistido.setName(userRecebido.getName());
        }
        if (!Util.emBranco(userRecebido.getPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userPersistido.setPassword(encoder.encode(userRecebido.getPassword()));
        }
        //atualizaTelefones(usuarioPersistido);
    }

    private void copiarValores(UserDTO usuarioRecebido, User userPersistido) {

        if(Util.naoNuloeVazio(usuarioRecebido.getTelefones())) {
            userPersistido.getPhones().clear();
            userPersistido.setPhones(atualizaTelefones(usuarioRecebido, userPersistido));
        } else {
            userPersistido.getPhones().clear();
        }
        if (!Util.emBranco(usuarioRecebido.getNome()) && !usuarioRecebido.getNome().equals(userPersistido.getName())) {
            userPersistido.setName(usuarioRecebido.getNome());
        }
        if (!Util.emBranco(usuarioRecebido.getSenha())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userPersistido.setPassword(encoder.encode(usuarioRecebido.getSenha()));
        }
    }

    private void apagaTelefones(List<Phone> phones) {
        telefoneService.deleteAll(phones);
    }

    private List<Phone> atualizaTelefones(UserDTO usuarioRecebido, User userPersistido) {
        List<Phone> telefonesRetorno = new ArrayList<>();
        for (PhoneDTO telefone : usuarioRecebido.getTelefones()) {
            telefonesRetorno.add(Phone.builder()
                    .id(telefone.getId())
                    .ddi(telefone.getDdi())
                    .ddd(telefone.getDdd())
                    .numero(telefone.getNumero())
                    .user(userPersistido)
                    .build());
        }
        return telefonesRetorno;
    }

    public User returnUserByID(String email) {
        return repository.findByEmail(email);
    }
}
