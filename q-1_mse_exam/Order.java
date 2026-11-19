
package mseexam;

public class Order {

    private int order_id;
    private String customer_name;
    private String product_name;
    private int quantity;
    private double unit_price;
    private String status;

    public Order() {
    }

    public Order(int order_id, String customer_name,
                 String product_name, int quantity,
                 double unit_price, String status) {

        this.order_id = order_id;
        this.customer_name = customer_name;
        this.product_name = product_name;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.status = status;
    }

    public int getOrder_id() {
        return this.order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_name() {
        return this.customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return this.unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
