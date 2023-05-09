package configs;

@SuppressWarnings("unused")
public class Movimentacao {
	
	
	public Integer conta_id;
	public String descricao;
	public String envolvido;
	public String tipo;
	public String data_transacao;
	public String data_pagamento;
	public Float valor;
	public Boolean status;

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