package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class ProdutoSteps {


    //Support

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
                                "    \"password\": \"teste\",\n" +
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

    }

    @Quando("envio a solicitação para criar um produto")
    public void envio_a_solicitação_para_criar_um_produto() {

    }

    @Então("valido a mensagem de sucesso da criação do produto")
    public void valido_a_mensagem_de_sucesso_da_criação_do_produto() {

    }

    @Dado("que desejo consultar um produto com sucesso passando ID")
    public void que_desejo_consultar_um_produto_com_sucesso_passando_id() {

    }

    @Quando("envio a solicitação para listar um produto")
    public void envio_a_solicitação_para_listar_um_produto() {

    }

    @Então("faço a validação da mensagem de retorno da consulta do produto")
    public void faço_a_validação_da_mensagem_de_retorno_da_consulta_do_produto() {

    }


}
