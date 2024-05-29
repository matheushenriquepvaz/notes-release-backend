package br.com.notesrelease.dtos;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

    private Long id;

    @JsonBackReference("usuario-telefone")
    private UserDTO usuario;

    private String ddi;

    private String ddd;

    private String numero;


}