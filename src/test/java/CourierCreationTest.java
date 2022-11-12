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


public class CourierCreationTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.courierDefault();
        courierClient = new CourierClient();
    }

//Удаление курьера после теста
    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("api.courier.Courier can be created successfully")
    @Description("Service will return 201 Created when new courier is created")
    public void courierCanBeCreatedTest() {
        //Создание курьера
        ValidatableResponse response = courierClient.create(courier);
        //Авторизация курьера
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        // Проверка успешного создания курьера
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("api.courier.Courier is not created", isCreated);
        //Проверка авторизации курьера
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("api.courier.Courier is not login", SC_OK, loginStatusCode);
        courierId = loginResponse.extract().path("id");
        //Проверка успешного запроса на возврат id
        assertNotNull("Id is null", courierId);
    }
}
