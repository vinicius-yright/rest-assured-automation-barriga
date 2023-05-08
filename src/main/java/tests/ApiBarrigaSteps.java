package tests;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import configs.BaseTest;
import configs.Data;
import configs.Movimentacao;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApiBarrigaSteps extends BaseTest{
	
	private String token;
	private static String contaNameRandom = "Account " + System.nanoTime();
	private static Integer contaId;
	private static Integer idTransacao;


	@Given("^that i want to create a new account$")
	public void thatIWantToCreateNewAccount() {
		setup();
	}

	@And("^i retrieve the authentication token from the /signin endpoint$")
	public void iSetUpTheAppropriatePayload() {
		setup();
		Map<String, String> data = new HashMap<String, String>();
		data.put("email", "death@test");
		data.put("senha", "death");

		token = given().contentType(APP_CONTENT_TYPE).body(data)
				.when().post("/signin")
				.then().log().all().statusCode(200).extract().path("token");
	}

	@When("^i post the request to create the new account$")
	public void iPostTheRequestToCreateTheNewAccount() {
		contaId = given().header("Authorization", "JWT " + token).body("{ \"nome\": \""+contaNameRandom+"\" }").when().post("/contas").then()
				.statusCode(201).extract().path("id");
	}

	@Then("^i can get the account's informations through its id$")
	public void iCanGetAccountInfoThroughId() {
		given().header("Authorization", "JWT " + token).when().get("/contas/" + contaId).then()
				.statusCode(200).body("nome", is(contaNameRandom));
	}

	@Given("^that i want to edit a new account$")
	public void thatIWantToEditANewAccount() {
		setup();
	}

	@When("^i post the request to update the account's name$")
	public void iPostTheRequestToEditTheAccountSName() {
		contaId = given().header("Authorization", "JWT " + token).body("{ \"nome\": \""+contaNameRandom+" updated\" }").pathParam("id", "1734061")
				.when().put("/contas/{id}").then()
				.statusCode(200).body("nome", is(contaNameRandom + " updated")).extract().path("id");
	}

	@Then("^i can verify that the name's been changed successfully$")
	public void iCanVerifyTheNamesChangedSuccessfully() {
		given().header("Authorization", "JWT " + token).when().get("/contas/" + contaId).then()
				.statusCode(200).body("nome", is(contaNameRandom+ " updated"));
	}

	@Given("^that i want to insert banking movement into an account$")
	public void thatIWantToInsertBankingMovementIntoAnAccount() {
		setup();
	}

	@When("^i post the request to insert banking movement$")
	public void iPostTheRequestToInsertBankingMovement() {
		Movimentacao mv = new Movimentacao(1734061, "Deposit", "Vinicius Souza", "REC", Data.getData(-1), Data.getData(6), 300f, true);

		idTransacao = given().header("Authorization", "JWT " + token).body(mv).when().post("/transacoes")
				.then().statusCode(201).extract().path("id");
	}

	@Then("^i can verify that the transaction's been successfully inserted through its id$")
	public void iCanVerifyThatTheTransactionSBeenSuccessfullyInsertedThroughItsId() {
		given().header("Authorization", "JWT " + token).when().get("/transacoes")
				.then().statusCode(200).assertThat()
				.body("conta_id", Matchers.hasItem(1734061))
				.body("descricao", Matchers.hasItem("Deposit"))
				.body("envolvido", Matchers.hasItem("Vinicius Souza"))
				.body("valor", Matchers.hasItem("300.00"));
	}

	@When("^i delete the banking movement that i've just created$")
	public void iDeleteTheBankingMovementThatIVeJustCreated() {
		given().header("Authorization", "JWT " + token).pathParam("id", idTransacao).when().delete("/transacoes/{id}")
				.then().statusCode(204);

		given().header("Authorization", "JWT " + token).when().get("/transacoes")
				.then().statusCode(200).assertThat()
				.body("conta_id", Matchers.not(hasItem(1734061)));
	}

	@Then("^i can check if the delete operation has been successful$")
	public void iCanCheckIfTheDeleteOperationHasBeenSuccessful() {
	}

	@Test
	public void t01_acessarSemConta() {
		given().when().get("/contas").then().statusCode(401);
	}

	@Test
	public void t04_criarContaBancariaRepetida() {
		given().header("Authorization", "JWT " + token).body("{ \"nome\": \""+contaNameRandom+" qualquer_2\" }").when().post("/contas")
				.then().statusCode(400).body("error", is("J� existe uma conta com esse nome!"));
	}

	@Test
	public void t06_validarCamposObrigatoriosMovimentacaoNaConta() {
		given().header("Authorization", "JWT " + token).body("{}").when().post("/transacoes")
		.then().statusCode(400).body("$", hasSize(8));
	}
	
	@Test
	public void t07_validarDataTransacaoMovimentacaoNaConta() {
		Movimentacao mv = new Movimentacao(contaId, "Bank transfer", "Vinicius Souza", "REC", Data.getData(2), Data.getData(-1), 200f, true);
		given().header("Authorization", "JWT " + token).body(mv).when().post("/transacoes")
		.then().statusCode(400).body("msg", hasItem("Data da Movimenta��o deve ser menor ou igual � data atual"));
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

	}



}
