package br.com.notesrelease.Util;

import br.com.notesrelease.dominio.Enum.TypesOfComparition;

import javax.persistence.Query;

public class QueryUtils {

    public static String apendaCondicao(String query, String propriedade, String nomeParametro, boolean condicao) {
        if (condicao) {
            query += Constants.AND + propriedade + Constants.IGUAL + Constants.DOIS_PONTOS + nomeParametro;
        }
        return query;
    }

    public static String apendaCondicaoCollection(String query, String propriedade, String nomeParametro, boolean condicao) {
        if (condicao) {
            query += Constants.AND + propriedade + Constants.IN + Constants.PARENTESE_ESQUERDO + Constants.DOIS_PONTOS + nomeParametro + Constants.PARENTESE_DIREITO;
        }
        return query;
    }

    public static String apendaCondicaoLike(String query, String propriedade, String nomeParametro, boolean condicao) {
        if (condicao) {
            query += Constants.AND + Constants.UPPER + Constants.PARENTESE_ESQUERDO + propriedade + Constants.PARENTESE_DIREITO + Constants.LIKE + Constants.UPPER + Constants.PARENTESE_ESQUERDO + Constants.DOIS_PONTOS + nomeParametro + Constants.PARENTESE_DIREITO;
        }
        return query;
    }

    public static String apendaCondicaoData(String query, String propriedade, String nomeParametro, TypesOfComparition tipo, boolean condicao) {
        if (condicao) {
            query += Constants.AND + propriedade + tipo.getDescricao() + Constants.DOIS_PONTOS + nomeParametro;
        }
        return query;
    }

    public static Query apendaValorParametro(Query query, String nomeParametro, Object valor, boolean condicao) {
        if (condicao) {
            query.setParameter(nomeParametro, valor);
        }
        return query;
    }
    
}
