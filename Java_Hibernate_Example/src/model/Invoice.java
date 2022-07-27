package model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Invoice {
    private Long id;
    private LocalDate invoiceDate;
    private LocalDate deliveryDate;

    public Invoice(LocalDate invoiceDate, LocalDate deliveryDate) {
        this.invoiceDate = invoiceDate;
        this.deliveryDate = deliveryDate;
    }

    public Invoice() {

    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }

    private OrderN orderN;

    @OneToOne(optional = false)
    public OrderN getOrder() {
        return orderN;
    }

    public void setOrder(OrderN orderN) {
        this.orderN = orderN;
    }


}
