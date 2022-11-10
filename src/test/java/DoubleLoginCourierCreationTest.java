import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class DoubleLoginCourierCreationTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.courierDefault();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Client can't create courier with double login")
    @Description("Service will return code 400 when client create courier with double login")
    public void createDoubleLoginTest() {
        //Создание курьера и проверка
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isCreated);
        //Логин для получения id
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Courier is not login", SC_OK, loginStatusCode);
        courierId = loginResponse.extract().path("id");
        assertNotNull("Id is null", courierId);
        //Повторное создание курьера с тем же логином
        ValidatableResponse doubleResponse = courierClient.create(courier);
        int doubleStatusCode = doubleResponse.extract().statusCode();
        assertEquals("Status code is incorrect",SC_CONFLICT, doubleStatusCode);
    }
}
