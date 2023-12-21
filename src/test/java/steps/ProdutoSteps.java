package steps;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ProdutoSteps {

    String mensagem = "";

    String id = "";

    int statusCode;

    ValidatableResponse validatableResponse;

    String url = "https://serverest.dev/produtos";

    String url_lista_produto = "https://serverest.dev/produtos?_id="+id;



    //Support


    public String nomeObjetoGerado(){
        Faker faker = new Faker(new Locale("pt-BR"));
        return faker.commerce().material();
    }
    public String nomeGerado(){
        Faker faker = new Faker(new Locale("pt-BR"));
        return faker.name().fullName();
    }
    public static String getToken() {
        ValidatableResponse validatableResponse;
        String url = "https://serverest.dev/login";
        String token;

        validatableResponse =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .body("{\n" +
                                "    \"email\": \"beltrano@qa.com.br\",\n" +
                                "    \"password\": \"teste\"\n" +
                                "}")
                        .when()
                        .post(url)
                        .then()
                        .assertThat()
                        .log().all();
        token = validatableResponse.extract().path("authorization");
        return token;
    }


    //Fim Support

    @Dado("que preenchi o body com sucesso")
    public void que_preenchi_o_body_com_sucesso() {
        get(url);
    }

    @Quando("envio a solicitação para criar um produto")
    public void envio_a_solicitação_para_criar_um_produto() {
        validatableResponse = given()
                .header("Authorization",getToken())
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(

                        "{\n" +
                                "  \"nome\": \""+nomeObjetoGerado()+"\",\n" +
                                "  \"preco\": 900,\n" +
                                "  \"descricao\": \""+nomeObjetoGerado()+"\",\n" +
                                "  \"quantidade\":  5\n" +
                                "}\n")
                .urlEncodingEnabled(false)
                .post(url)
                .then()
                .assertThat()
                .log().all();

        mensagem = validatableResponse.extract().path("message");
        statusCode = validatableResponse.extract().statusCode();
        id = validatableResponse.extract().path("_id");
    }

    @Então("valido a mensagem de sucesso da criação do produto")
    public void valido_a_mensagem_de_sucesso_da_criação_do_produto() {
        assertEquals("Cadastro realizado com sucesso", mensagem);
        assertEquals(201, statusCode);
    }

    @Dado("que desejo consultar um produto com sucesso passando ID")
    public void que_desejo_consultar_um_produto_com_sucesso_passando_id() {
           get(url_lista_produto);
    }

    @Quando("envio a solicitação para listar um produto")
    public void envio_a_solicitação_para_listar_um_produto() {
        url_lista_produto = "https://serverest.dev/produtos?_id=f2D96naOjfBTyNPD";

        validatableResponse =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(url_lista_produto)
                        .then()
                        .assertThat()
                        .log().all();
        mensagem = validatableResponse.extract().path("message");
        statusCode = validatableResponse.extract().statusCode();

    }

    @Então("faço a validação da mensagem de retorno da consulta do produto")
    public void faço_a_validação_da_mensagem_de_retorno_da_consulta_do_produto() {

        assertEquals(200, statusCode);
    }


}
