package br.com.notesrelease.infra.exceptions;


import lombok.Getter;
import lombok.Setter;

public class NegocioException extends Exception {

    @Getter
    @Setter
    private String mensagem;

    public NegocioException () {

    }

    public NegocioException (String mensagem) {
        super(mensagem);
        this.mensagem = mensagem;
    }

    public NegocioException (String mensagem, Exception e) {
        this.mensagem = mensagem;
    }

    public void tratar() throws Exception {
        throw new Exception(this.mensagem, this);
    }
}
