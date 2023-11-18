package stepDefinitions.api;


import hooks.api.HooksAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import utilities.ConfigReader;
import utilities.ReusableMethods;

import java.util.Arrays;

import static hooks.api.HooksAPI.spec;
import static io.restassured.RestAssured.given;

public class CommonAPI {

    String fullPath;

    JSONObject reqBody;

    Response response;

    @Given("Api kullanicisi {string} path parametreleri set eder.")
    public void api_kullanicisi_path_parametreleri_set_eder(String rawPaths) {

        fullPath = ReusableMethods.pathParameters(rawPaths);

    }
    @Then("AllCountries icin Get request gonderilir.")
    public void all_countries_icin_get_request_gonderilir() {

        response =ReusableMethods.getRequest(fullPath);

        response.prettyPrint();

    }


    @Then("Login icin {string} ve {string} girilir.")
    public void loginIcinVeGirilir(String email, String password) {

        /*
        {
          "email": "test@test.com",
          "password": "123123123"
        }
         */

        reqBody = new JSONObject();

        reqBody.put("email", ConfigReader.getProperty(email));
        reqBody.put("password", ConfigReader.getProperty(password));

    }

    @Then("Login icin Post request gonderilir.")
    public void loginIcinPostRequestGonderilir() {

        Response response = given()
                                .spec(spec)
                                .contentType(ContentType.JSON)
                                .header("Accept","application/json")
                            .when()
                                .body(reqBody.toString())
                                .post(fullPath);

        response.prettyPrint();


    }
}
