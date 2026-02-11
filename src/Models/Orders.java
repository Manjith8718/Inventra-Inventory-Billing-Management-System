package Models;


import java.time.LocalDateTime;

public class Orders {

    private int orderId;
    private String invoiceNumber;
    private double totalAmount;
    private LocalDateTime orderDate;


    public Orders() {
    }


    public Orders(String invoiceNumber, double totalAmount) {
        this.invoiceNumber = invoiceNumber;
        this.totalAmount = totalAmount;
    }


    public Orders(int orderId, String invoiceNumber, double totalAmount, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.invoiceNumber = invoiceNumber;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }



    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}

