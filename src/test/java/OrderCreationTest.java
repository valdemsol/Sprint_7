import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class OrderCreationTest {

    private Order order;
    private OrderClient orderClient;
    private int orderTrack;
    private final String[] color;

    public OrderCreationTest(String[] color) {
        this.color = color;
    }

    //Параметризация цветов
    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{""}}
        };
    }

    @Before
    public void setUp() {
        order = OrderGenerator.getDefault(color);
        orderClient  = new OrderClient();
    }

    @Test
    @DisplayName("Order can be created Successfully")
    @Description("Service will return 201 Created when new order is created")
    public void orderCanBeCreatedTest() {
        //Создание заказа и проверяем код статуса
        ValidatableResponse response = orderClient.create(order);
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        orderTrack = response.extract().path("track");
        //Проверка, что трэк не равен нулю
        assertNotNull("Track is null", orderTrack);
    }

}
