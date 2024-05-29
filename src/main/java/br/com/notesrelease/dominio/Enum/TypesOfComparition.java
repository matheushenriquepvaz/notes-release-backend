package br.com.notesrelease.dominio.Enum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TypesOfComparition {
    IGUAL(1, " = "),
    DIFERENTE(2, " != "),
    MENOR(3, " < "),
    MAIOR(4, " > "),
    MENOR_IGUAL(5, " <= "),
    MAIOR_IGUAL(6, " >= "),
    IS(6, " is "),
    IS_NOT(6, " is not ");

    private int codigo;
    private String descricao;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    TypesOfComparition(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public List<TypesOfComparition> listarTodos() {
        return new ArrayList<TypesOfComparition>(Arrays.asList(TypesOfComparition.values()));
    }
}
