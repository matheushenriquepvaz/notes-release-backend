package br.com.notesrelease.infra.exceptions;

public class Errors {

    public static final String ERRO_GENERICO = "Não foi posssível executar a solicitação, entre em contato com o responsável pelo sistema";

    public static final String USUARIO_SEM_PERMISSAO = "O usuário não possui permissão necessária para executar essa ação";

    public static final String INFORMACOES_INCOMPLETAS = "Não foi possível executar a ação com os parâmetros informados.";

    public static final String ELEMENTOS_OBRIGATORIOS_NAO_PREENCHIDOS = "Elementos obrigatórios não encontrados a partir dos parâmetros informados";

    public static final String RECURSO_NAO_ENCONTRADO = "O recurso buscado não pode ser encontrado.";

    public static final String PROCESSO_JA_ASSINADO = "O processo encontrado já possui uma assinatura.";
}

