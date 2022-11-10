public class CourierGenerator {

    public static Courier courierDefault() {
        return new Courier("valdemsol", "4321", "bobby");
    }

    public static Courier courierWithoutPass() {
        return new Courier ("apple", "", "johnny");
    }

    public static Courier reggedCourier() {
        return new Courier ("melon", "1234", "tomas");
    }

    public static Courier notReggedCourier() {
        return new Courier("potatoe", "1111", "smith");
    }
}
