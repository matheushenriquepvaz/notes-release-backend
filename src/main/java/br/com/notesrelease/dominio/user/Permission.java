package br.com.notesrelease.dominio.user;

/**
 * Created by DEINF.MHVAZ on 27/03/2019.
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PER_PERMISSAO", uniqueConstraints={@UniqueConstraint(columnNames={"PER_IDENTIFICADOR"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PER_ID")
    private Long id;

    @Column(name = "PER_IDENTIFICADOR", nullable = false)
    private String identificador;

    /*
    @ManyToMany(mappedBy = "permissoes", fetch = FetchType.LAZY)
    @JsonBackReference(value = "per-usr")
    private List<Usuario> usuarios;
    */
    /*
    @ManyToMany(mappedBy = "permissoes")
    @JsonBackReference(value = "permissao-grp")
    private List<Grupo> grupos;

     */

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
        Permission other = (Permission) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Permissao [id=" + id + ", nome=" + identificador + "]";
    }
}
