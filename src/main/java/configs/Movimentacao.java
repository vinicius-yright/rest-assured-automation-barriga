package configs;

@SuppressWarnings("unused")
public class Movimentacao {
	
	
	private Integer conta_id;
	private String descricao;
	private String envolvido;
	private String tipo;
	private String data_transacao;
	private String data_pagamento;
	private Float valor;
	private Boolean status;

	public Movimentacao(Integer conta_id, String descricao, String envolvido, String tipo, String data_transacao,
			String data_pagamento, Float valor, Boolean status) {
		this.conta_id = conta_id;
		this.descricao = descricao;
		this.envolvido = envolvido;
		this.tipo = tipo;
		this.data_transacao = data_transacao;
		this.data_pagamento = data_pagamento;
		this.valor = valor;
		this.status = status;
	}

}