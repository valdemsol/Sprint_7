import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CourierLoginTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.reggedCourier();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Login of registered courier")
    @Description("Service will return 200 when courier login")
    public void courierCanLoginTest () {
        //Процесс логина
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        //Проверка, что логин прошел успешно
        assertEquals("Courier is not login", SC_OK, loginStatusCode);
        courierId = loginResponse.extract().path("id");
        //Проверка, что id не равен нулю
        assertNotNull("Id is null", courierId);
    }

}
