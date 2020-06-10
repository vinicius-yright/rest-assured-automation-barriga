package br.com.vinicius.tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.com.vinicius.configs.BaseTest;
import br.com.vinicius.configs.Data;
import br.com.vinicius.configs.Movimentacao;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiBarrigaTests extends BaseTest{
	
	private String token;
	private static String contaNameRandom = "Conta " + System.nanoTime();
	private static Integer contaId;
	private static Integer idTransacao;
	@Before
	public void loginEToken() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("email", "death@test");
		info.put("senha", "death");
		
		token = given().body(info).when().post("/signin").then().log().all().statusCode(200).extract().path("token");
	}
	
	@Test
	public void t01_acessarSemConta() {
		given().when().get("/contas").then().statusCode(401);
	}
	
	@Test
	public void t02_criarContaBancaria() {
		contaId = given().header("Authorization", "JWT " + token).body("{ \"nome\": \""+contaNameRandom+"\" }").when().post("/contas").then()
		.statusCode(201).extract().path("id");
	}
	
	@Test
	public void t03_alterarConta() {
		given().header("Authorization", "JWT " + token).body("{ \"nome\": \""+contaNameRandom+" qualquer_2\" }").pathParam("id", contaId)
		.when().put("/contas/{id}").then()
		.statusCode(200).body("nome", is(contaNameRandom + " qualquer_2"));
	}
	
	@Test
	public void t04_criarContaBancariaRepetida() {
		given().header("Authorization", "JWT " + token).body("{ \"nome\": \""+contaNameRandom+" qualquer_2\" }").when().post("/contas")
		.then().statusCode(400).body("error", is("Já existe uma conta com esse nome!"));
	}
	
	@Test
	public void t05_inserirMovimentacaoNaConta() {
		Movimentacao mv = new Movimentacao(contaId, "zapzap", "Suiciniv", "REC", Data.getData(-1), Data.getData(6), 200f, true);
		
		idTransacao = given().header("Authorization", "JWT " + token).body(mv).when().post("/transacoes")
		.then().statusCode(201).extract().path("id");
	}
	
	@Test
	public void t06_validarCamposObrigatoriosMovimentacaoNaConta() {
		given().header("Authorization", "JWT " + token).body("{}").when().post("/transacoes")
		.then().statusCode(400).body("$", hasSize(8));
	}
	
	@Test
	public void t07_validarDataTransacaoMovimentacaoNaConta() {
		Movimentacao mv = new Movimentacao(contaId, "zapzap", "Suiciniv", "REC", Data.getData(2), Data.getData(-1), 200f, true);
		System.out.println("TESTE 07");
		given().header("Authorization", "JWT " + token).body(mv).when().post("/transacoes")
		.then().statusCode(400).body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"));
	}
	
	@Test
	public void t08_validarRemocaoContaComTransacao() {
		given().header("Authorization", "JWT " + token).pathParam("id", contaId).when().delete("/contas/{id}")
		.then().statusCode(500).body("constraint", is("transacoes_conta_id_foreign"));
	}
	
	@Test
	public void t09_getSaldoContas() {
		given().header("Authorization", "JWT " + token).when().get("/saldo")
		.then().statusCode(200).body("find{it.conta_id == "+contaId+"}.saldo", greaterThan("0"));
	}
	
	@Test
	public void t10_removerMovimentacaoContas() {
		given().header("Authorization", "JWT " + token).pathParam("id", idTransacao).when().delete("/transacoes/{id}")
		.then().statusCode(204);
	}

}
