package api.courier;

import api.utils.RestBase;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestBase {

    private static final String COURIER_PATH  = "api/v1/courier";
    private static final String LOGIN_COURIER = "api/v1/courier/login";
    private static final String DELETE_COURIER = "api/v1/courier/";


    @Step("Create new courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .body (courier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step ("api.courier.Courier login")
    public ValidatableResponse login(Credentials credentials ) {
        return given()
                .spec(getBaseSpec())
                .body (credentials)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }

    @Step ("Delete courier")
    public ValidatableResponse delete (int id) {
        return given()
                .spec(getBaseSpec())
                .pathParam("id", id)
                .when()
                .delete(DELETE_COURIER + "{id}")
                .then();
    }
}