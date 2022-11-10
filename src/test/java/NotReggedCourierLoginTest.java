import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.junit.Assert.assertEquals;

public class NotReggedCourierLoginTest {

    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courier = CourierGenerator.notReggedCourier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Not registered courier can't login")
    @Description("Service will return 404 when not registered courier try to login")
    public void courierCantLoginTest () {
        //Логин незарегистрированным курьером
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        //Проверка, что логин не удался
        assertEquals("Status code is incorrect", SC_NOT_FOUND, loginStatusCode);
    }
}
