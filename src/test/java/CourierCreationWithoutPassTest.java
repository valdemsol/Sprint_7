import api.courier.Courier;
import api.courier.CourierClient;
import api.courier.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CourierCreationWithoutPassTest {

    private Courier courier;
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courier = CourierGenerator.courierWithoutPass();
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("api.courier.Courier creation without password")
    @Description("Service will return 400 Bad Request if courier creation will be without password")
    public void courierCannotBeCreatedTest () {
        //Создание и проверка, что процесс не удался
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);
    }
}
