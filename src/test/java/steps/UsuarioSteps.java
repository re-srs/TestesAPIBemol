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

public class UsuarioSteps {


    String mensagem = "";

    String id = "";

    int statusCode;

    ValidatableResponse validatableResponse;

    String url = "https://serverest.dev/usuarios";
    String url_lista_usuario = "https://serverest.dev/usuarios?_id="+id;


    //correto seria criar uma classe apartada, mas não consegui dar um extends na classe, aí fiz assim mesmo,
    //mas estou ciente que não é uma boa prática fazer dessa forma

    //////INICIO das classes de suporte

    public String nomeGerado(){
        Faker faker = new Faker(new Locale("pt-BR"));
        return faker.name().fullName();
    }
    public String emailGerado() {
        Faker faker = new Faker(new Locale("pt-BR"));
        String nomeCompleto = faker.name().fullName();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("pt-BR"), new RandomService());
        String email = fakeValuesService.letterify(nomeCompleto).replace(".", "");
        email = StringUtils.deleteWhitespace(email + "@qa.com.br");
        return email;
    }



    //////FIM das classes de suporte

    @Dado("que preenchi o body")
    public void que_preenchi_o_body() {
        get(url);
    }
    @Quando("envio a solicitação para criar um usuario")
    public void envio_a_solicitação_para_criar_um_usuario() {
        validatableResponse = given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(

                        "{\n" +
                        "  \"nome\": \""+nomeGerado()+"\",\n" +
                        "  \"email\": \""+emailGerado()+"\",\n" +
                        "  \"password\": \"teste\",\n" +
                        "  \"administrador\": \"true\"\n" +
                        "}\n")
                .urlEncodingEnabled(false)
                .post(url)
                .then()
                .assertThat()
                .log().all();

        mensagem = validatableResponse.extract().path("message");
        statusCode = validatableResponse.extract().statusCode();
 ;      id = validatableResponse.extract().path("_id");

    }
    @Então("valido a mensagem de sucesso da criação do usuario")
    public void valido_a_mensagem_de_sucesso_da_criação_do_usuario() {
        assertEquals("Cadastro realizado com sucesso", mensagem);
        assertEquals(201, statusCode);

    }

    @Dado("que desejo consultar um usuario com sucesso passando ID")
    public void que_desejo_consultar_um_usuario_com_sucesso_passando_id() {

        get(url_lista_usuario);

    }
    @Quando("envio a solicitação para listar um usuario")
    public void envio_a_solicitação_para_listar_um_usuario() {
        url_lista_usuario = "https://serverest.dev/usuarios?_id=3wd4SivWFSjrCXNV";

        validatableResponse =
                given()
                        .log().all()
                        .contentType(ContentType.JSON)
                        .when()
                        .get(url_lista_usuario)
                        .then()
                        .assertThat()
                        .log().all();
        mensagem = validatableResponse.extract().path("message");
        statusCode = validatableResponse.extract().statusCode();
    }
    @Então("faço a validação da mensagem de retorno da consulta")
    public void faço_a_validação_da_mensagem_de_retorno_da_consulta() {
        assertEquals(200, statusCode);

    }


}
