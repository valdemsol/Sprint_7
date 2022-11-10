import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestBase {

    private static final String ORDER_PATH = "api/v1/orders";

    @Step("Create new order")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .body (order)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Get orders")
    public ValidatableResponse allOrders() {
        return given()
                .spec(getBaseSpec())
                .get(ORDER_PATH)
                .then();
    }

}
