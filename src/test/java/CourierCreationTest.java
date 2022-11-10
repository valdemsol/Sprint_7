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
    @DisplayName("Courier can be created successfully")
    @Description("Service will return 201 Created when new courier is created")
    public void courierCanBeCreatedTest() {
        //Создание и проверка, что курьер был создан
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        boolean isCreated = response.extract().path("ok");
        assertTrue("Courier is not created", isCreated);
        //Авторизация курьера
        ValidatableResponse loginResponse = courierClient.login(Credentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        //Проверка успешной авторизации
        assertEquals("Courier is not login", SC_OK, loginStatusCode);
        courierId = loginResponse.extract().path("id");
        //Проверка успешного запроса на возврат id
        assertNotNull("Id is null", courierId);

    }


}
