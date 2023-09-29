package org.example.model;

import java.util.Date;
import java.util.List;

public class Invoice {
    private int id;
    private String customer;
    private Date invDate;
    private Float total;
    private Float discount;
    private Float tendered;
    private List<InvoiceItem> products;

    public Invoice(String customer, Date invDate, Float total, Float discount, Float tendered, List<InvoiceItem> products) {
        this.customer = customer;
        this.invDate = invDate;
        this.total = total;
        this.discount = discount;
        this.tendered = tendered;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getInvDate() {
        return invDate;
    }

    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTendered() {
        return tendered;
    }

    public void setTendered(Float tendered) {
        this.tendered = tendered;
    }

    public List<InvoiceItem> getProducts() {
        return products;
    }

    public void setProducts(List<InvoiceItem> products) {
        this.products = products;
    }
}
