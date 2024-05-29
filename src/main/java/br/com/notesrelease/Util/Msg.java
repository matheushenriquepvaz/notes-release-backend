package br.com.notesrelease.Util;


import lombok.Getter;

/**
 * Define as mensagens mais comuns utilizadas no sistema.
 * 
 * @author leandro.vieira
 */
@Getter
public enum Msg {

//****************************************************************
	MNA003_DATA_VENCIMENTO(Msg.TIPO_ALERTA, "Data de vencimento {0} é menor ou igual a data atual."),
	MNA005_CAMPO_OBRIGATORIO(Msg.TIPO_ALERTA, "Campo obrigatório {1}''{0}'' não foi informado!"),
	MNA005_CAMPOS_OBRIGATORIOS(Msg.TIPO_ALERTA, "Campos obrigatórios {1}({0}) não foram informados!"),
//****************************************************************
	MNI000_VALIDACAO_INTERNA_PADRAO(Msg.TIPO_INTERNA_SISTEMA, "Foi encontrada uma inconsistência interna. Contacte o administrador do sistema para mais informações."),
	MNI001_REGISTRO_INVALIDO(Msg.TIPO_INTERNA_SISTEMA, "A informação de ''{0}'' não é válida!"),
	MNI002_GENERICA(Msg.TIPO_INTERNA_SISTEMA, "{0}"),
//****************************************************************
	MNE001_PARAMETRO_REMESSA_DCB(Msg.TIPO_ERRO, "O local para remessa do arquivo DCB não está parametrizado no SDJ."),
	MNE002_PARAMETRO_BACEN(Msg.TIPO_ERRO, "Erro ao buscar taxas do bacen."),
//****************************************************************
//****************************************************************
	MNA009(Msg.TIPO_ALERTA, "Data fim não pode ser menor que a data início"),
	MNA019(Msg.TIPO_ALERTA, "Data inícial e data final não pode ser maior que a data de hoje."),
	MNA026(Msg.TIPO_ALERTA, "Conta judicial informada não encontrada."),
	MNA028(Msg.TIPO_ALERTA, "Não é permitido preencher campo de ORIGEM ou DESTINO de crédito se o campo correspondente de débito estiver preenchido ou vice-versa."),
	MNA029(Msg.TIPO_ALERTA, "Data de Movimento menor que 30 dias da data atual."),
	MNA034(Msg.TIPO_ALERTA, "Data de Movimento maior que a data atual."),
	MNA036(Msg.TIPO_ALERTA, "Lançamento do tipo débito não pode ser retroativo."),
	MNA035(Msg.TIPO_ALERTA, "O Valor do Movimento deve ser diferente de ZERO.ba"),
	MNA057(Msg.TIPO_ALERTA, "O órgão conveniado da conta judicial informada não admite depósito em continuação."),
	//****************************************************************
	MNA014_REGISTRO_EM_DUPLICIDADE(Msg.TIPO_ALERTA, "Registro em duplicidade!"),
	MNE_PARAMETRO_LOCAL_REGISTRO_SAP_NAO_ENCONTRADO(Msg.TIPO_ALERTA, "O local de registro do arquivo do SAP não parametrizado."),
	MNA022_LOCAL_GRAVACAO_ARQUIVO_SAP_INEXISTENTE(Msg.TIPO_ALERTA, "Local de gravação do arquivo SAP é inexistente."),
	MNA023_NAO_EXISTE_REGISTRO_ENVIO_ARQUIVO_SAP(Msg.TIPO_ALERTA, "Não existe(m) registro(s) a ser enviado(s) no arquivo SAP."),
	MNA024_SEM_PERMISSAO_LEITURA_ESCRITA_ENVIO_SAP(Msg.TIPO_ALERTA, "O sistema SDJ não tem permissão de leitura/escrita na pasta do arquivo de envio para o SAP."),
	MNA025_PROBLEMA_TRANSMISSAO_ARQUIVO_SAP(Msg.TIPO_ALERTA, "Problemas na transmiss\u00E3o do arquivo para o SAP."),

//****************************************************************
	MNE_PARAMETRO_LOCAL_REGISTRO_ELIN_DUXUS_NAO_ENCONTRADO(Msg.TIPO_ALERTA, "O local de registro do arquivo do ELIN DUXUS não parametrizado."),
	MNA022_LOCAL_GRAVACAO_ARQUIVO_ELIN_DUXUS_INEXISTENTE(Msg.TIPO_ALERTA, "Local de gravação do arquivo ELIN DUXUS é inexistente."),
	MNA023_NAO_EXISTE_REGISTRO_ENVIO_ARQUIVO_ELIN_DUXUS(Msg.TIPO_ALERTA, "Não existe(m) registro(s) a ser enviado(s) no arquivo ELIN DUXUS."),
	MNA024_SEM_PERMISSAO_LEITURA_ESCRITA_ENVIO_ELIN_DUXUS(Msg.TIPO_ALERTA, "O sistema SDJ não tem permissão de leitura/escrita na pasta do arquivo de envio para o ELIN DUXUS."),
	MNA025_PROBLEMA_TRANSMISSAO_ARQUIVO_ELIN_DUXUS(Msg.TIPO_ALERTA, "Problemas na transmiss\u00E3o do arquivo para o ELIN DUXUS."),
//****************************************************************
	//Excecoes do caso de integração com o ATB
	MNA026_CONTA_JUDICIAL_NAO_ENCONTRADA(Msg.TIPO_ALERTA, "Conta judicial informada n\u00E3o encontrada."),
	MNE_VALOR_MOVIMENTACAO_MAIOR_SALDO(Msg.TIPO_ALERTA, "Valor da movimenta\u00E7\u00E3o maior que o valor do saldo."),
//****************************************************************

//****************************************************************
	//Excecoes dos status das contas
	MNA01_STATUS_CONTA_NAO_ACEITA_DEBITO(Msg.TIPO_ALERTA, "O status da conta - {0} - n\u00E3o permite opera\u00E7\u00E3o de d\u00e9bito."),
	MNA02_STATUS_CONTA_NAO_ACEITA_CREDITO(Msg.TIPO_ALERTA, "O status da conta - {0} - n\u00E3o permite opera\u00E7\u00E3o de cr\u00e9dito."),
	;
	
	private static final String TIPO_ALERTA = "1";
	private static final String TIPO_ERRO = "2";
	private static final String TIPO_INTERNA_SISTEMA = "3";
	private static final String PADRAO_SOMENTE_DIGITO = "[^0-9.]";

	@Getter
	private String descricao;

	@Getter
	private String tipo;

	Msg(String descricao, String tipo) {
		this.descricao = descricao;
		this.tipo = tipo;
	}

	//****************************************************************



// ****************************************************************

	/**
	 * Construtor padrão
	 * @param tipo
	 * @param descricao
	 */
	/*
	private Msg(String tipo, String descricao) {
		String cdExcecao = this.name().replaceAll(PADRAO_SOMENTE_DIGITO, Util.VAZIO);
		cdExcecao = StringUtils.isNumeric(cdExcecao) ? cdExcecao : Util.VAZIO;
	}
	*/


	//****************************************************************
	
	/**
	 * Retorna a descrição da mensagem aplicando os parâmetros passados.
	 * @param parametros Parametros a serem utilizados na mensagem.
	 * @return Mensagem formatada
	 */
	/*
	public String format(Object ... parametros) {
		return Util.aplicarFormatacao(this.getDescricao(), parametros);
	}

	 */
}