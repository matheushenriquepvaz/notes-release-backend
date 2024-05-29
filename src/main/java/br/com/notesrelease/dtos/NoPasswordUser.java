package br.com.notesrelease.dtos;

public class NoPasswordUser extends UserDTO {

    @Override
    public String getSenha() {
        return "";
    }

    @Override
    public void setSenha(String senha) {
        super.setSenha(super.getSenha());
    }
}
