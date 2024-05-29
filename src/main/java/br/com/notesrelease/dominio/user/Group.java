package br.com.notesrelease.dominio.user;

/**
 * Created by DEINF.MHVAZ on 27/03/2019.
 */

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "GRP_GRUPO", uniqueConstraints={@UniqueConstraint(columnNames={"GRP_NOME"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GRP_ID")
    private Long id;

    @Column(name = "GRP_NOME", nullable = false)
    private String nome;

    @Column(name = "GRP_DESC", nullable = false)
    private String descricao;

    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @JoinTable(
            name = "GRU_GRUPO_USUARIO",
            joinColumns = { @JoinColumn(name = "GRP_ID") },
            inverseJoinColumns = { @JoinColumn(name = "USR_ID") }
    )
    @JsonBackReference
    private List<User> users;

    @ManyToMany(cascade = { CascadeType.REFRESH }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "GUP_GRUPO_PERMISSAO",
            joinColumns = { @JoinColumn(name = "GRP_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PER_ID") }
    )
    private List<Permission> permissoes;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Group other = (Group) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Grupo [id=" + id + ", nome=" + nome + "]";
    }
}