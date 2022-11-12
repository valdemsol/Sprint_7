import api.courier.Courier;
import api.courier.CourierClient;
import api.courier.CourierGenerator;
import api.courier.Credentials;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class DoubleCourierCreationTest {

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
    @DisplayName("Client can't create double courier")
    @Description("Service will return 400 Bad Request when double courier is created")
    public void doubleCourierCreationTest() {
        //Создание курьера
        ValidatableResponse response = courierClient.create(courier);
        //Логин
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        //Повторное создание курьера
        ValidatableResponse doubleResponse = courierClient.create(courier);

        // Проверка успешного создания курьера
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("api.courier.Courier is not created", isCreated);
        //Проверка логина и получение id
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("api.courier.Courier is not login", SC_OK, loginStatusCode);
        courierId = loginResponse.extract().path("id");
        assertNotNull("Id is null", courierId);
        //Проверка, что повторное создание того же курьера вызывает ошибку
        int doubleStatusCode = doubleResponse.extract().statusCode();
        assertEquals("Status code is not 400",SC_CONFLICT, doubleStatusCode);
    }
}
