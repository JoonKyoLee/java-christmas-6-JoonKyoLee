package christmas.domain;

public class Customer {
    private final Date date;
    private final Order order;

    public Customer(Date date, Order order) {
        this.date = date;
        this.order = order;
    }
}