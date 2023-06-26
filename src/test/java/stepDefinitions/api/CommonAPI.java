package stepDefinitions.api;


import hooks.api.HooksAPI;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import utilities.ConfigReader;

import java.util.Arrays;

import static hooks.api.HooksAPI.spec;
import static io.restassured.RestAssured.given;

public class CommonAPI {

    String fullPath;

    JSONObject reqBody;

    @Given("Api kullanicisi {string} path parametreleri set eder.")
    public void api_kullanicisi_path_parametreleri_set_eder(String rawPaths) {

        // https://trendlifebuy.com/api/profile/allCountries

        // spec.pathParams("pp1","api","pp2","profile","pp3","allCountries");

        String [] paths = rawPaths.split("/"); // ["api","profile","allCountries"]

        System.out.println(Arrays.toString(paths));
       /*
        spec.pathParam("pp1","api");
        spec.pathParam("pp2","profile");
        spec.pathParam("pp3","allCountries");
        */

        StringBuilder tempPath = new StringBuilder("/{");


        for (int i = 0; i < paths.length; i++) {

            String key = "pp" + i;
            String value = paths[i].trim();

            HooksAPI.spec.pathParam(key,value);

            tempPath.append(key + "}/{");
        }
            tempPath.deleteCharAt(tempPath.lastIndexOf("{"));
            tempPath.deleteCharAt(tempPath.lastIndexOf("/"));

            fullPath = tempPath.toString();
        System.out.println("fullPath = " + fullPath);
    }
    @Then("AllCountries icin Get request gonderilir.")
    public void all_countries_icin_get_request_gonderilir() {

        Response response = given()
                                .spec(spec)
                                .contentType(ContentType.JSON)
                                .header("Accept","application/json")
                                .headers("Authorization","Bearer " + HooksAPI.token)
                            .when()
                                .get(fullPath);

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
