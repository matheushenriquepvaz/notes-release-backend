package br.com.notesrelease.dominio.user;

import br.com.notesrelease.Util.Util;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DEINF.MHVAZ on 15/03/2019.
 */
@Entity
@Table(name = "USR_USUARIO", uniqueConstraints={@UniqueConstraint(columnNames={"USR_EMAIL"})})
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "USR_ID")
    private Long id;

    @Column(name = "USR_NOME", nullable = false, length = 200)
    private String name;

    @Column(name = "USR_EMAIL", nullable = false, length = 200)
    private String email;

    @Column(name = "USR_SENHA", nullable = false, length = 200)
    private String password;

    @Column(name = "USR_ATIVO", nullable = false)
    private boolean active;

    @ManyToMany(mappedBy = "users")
    @JsonManagedReference
    private List<Group> groups;

    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "USP_USUARIO_PERMISSAO",
            joinColumns = { @JoinColumn(name = "USER_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PER_ID") }
    )
    private List<Permission> permissoes;

    @Column(name = "USR_HASH_TROCA_SENHA")
    private String hashTrocaDeSenha;

    @Column(name = "USR_DT_CRIACAO", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private Date dataCriacao;

    @Column(name = "USR_BLOQUEADO", nullable = false)
    private boolean bloqueado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "USR_ID")
    @Fetch(FetchMode.SUBSELECT)
    private List<Phone> phones;

    public void setPhones(List<Phone> phones) {
        if (!Util.nuloOuVazio(phones)) {
            if (Util.naoNuloeVazio(this.phones)) {
                this.phones = new ArrayList<>();
            }
            this.getPhones().clear();
            this.getPhones().addAll(phones);
        }
    }

}
