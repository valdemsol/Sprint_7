import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


public class GetOrdersListTest {

    private OrderClient orderClient;


    @Before
    public void setUp() {
        orderClient  = new OrderClient();
    }

    @Test
    @DisplayName("Getting orders list")
    @Description("Service will return 200 and orders list is not empty")
    public void clientCanGetOrdersTest() {
        //Получение списка заказов и проверка, что он не пуст
        ValidatableResponse response = orderClient.allOrders();
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, statusCode);
        ArrayList<String> orderBody = response.extract().path("orders");
        boolean isNotEmpty = orderBody!=null && !orderBody.isEmpty();
        assertTrue("Orders is empty", isNotEmpty);


    }

}
