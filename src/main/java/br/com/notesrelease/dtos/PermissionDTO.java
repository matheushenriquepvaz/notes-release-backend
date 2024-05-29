package br.com.notesrelease.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionDTO {

    public PermissionDTO() {
    }

    private Long id;

    private String identificador;
}
