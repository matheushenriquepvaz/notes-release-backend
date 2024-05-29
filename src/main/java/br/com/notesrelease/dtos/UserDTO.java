package br.com.notesrelease.dtos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    private String nome;

    private String nomeSocial;

    private String email;

    private String senha;

    private boolean ativo;

    private boolean bloqueado;

    private String ambiente;

    private String hashRetorno;

    private List<String> roles;

    private String cpf;

    private String rg;

    private Date dtAssinaturaTermo;

    private Date dataCriacao;

    @JsonManagedReference("usuario-telefone")
    private List<PhoneDTO> telefones;

}
