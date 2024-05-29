package br.com.notesrelease.dominio.user;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "TEL_TELEFONE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Phone {

    @Id
    @Column(name = "TEL_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USR_ID", referencedColumnName = "USR_ID", insertable=false, updatable=false)
    @Cascade({org.hibernate.annotations.CascadeType.REFRESH, org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private User user;

    @Column(name = "TEL_DDI", length = 3)
    private String ddi;

    @Column(name = "TEL_DDD" , length = 3)
    private String ddd;

    @Column(name = "TEL_NUMERO", nullable = false)
    private String numero;


}
