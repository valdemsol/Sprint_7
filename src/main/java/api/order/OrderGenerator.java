package api.order;

public class OrderGenerator {

    public static Order getDefault(String[] color) {
        return new Order("Artem",
                "Valerievich",
                "Chernyshevskogo",
                4,
                "+7 999 777 55 86",
                5,
                "2022-11-11",
                "Hurry up",
                color);
    }
}
