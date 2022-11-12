import api.courier.Courier;
import api.courier.CourierClient;
import api.courier.CourierGenerator;
import api.courier.Credentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

public class CourierLoginWithoutPassTest {

    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courier = CourierGenerator.courierWithoutPass();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("api.courier.Courier login without password")
    @Description("Service will return 400 Bad Request when api.courier.Courier try to login without password field")
    public void courierCannotBeLoggedTest () {
        //Логин курьера без пароля и проверка, что процесс не удался
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, loginStatusCode);
    }
}
