package model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class OrderN { //nie może być order bo słowo kluczowe
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate planedExecutionDate;
    private LocalDate realExecutionDate;
    private int orderValue; // amount of money customer is supposed to pay.
    private OrderState orderState;
    private ReceivementMethod receivementMethod;


    public OrderN() {}

    public enum OrderState{
        UNCONFIRMED, EXECUTED,  UNDER_EXECUTION, CANCELED
    }

    public enum ReceivementMethod {
        IN_PERSON, DELIVERY
    }



    public OrderN(LocalDate planedExecutionDate, LocalDate realExecutionDate, int orderValue, OrderState orderState, ReceivementMethod receivementMethod) {
        this.planedExecutionDate = planedExecutionDate;
        this.realExecutionDate = realExecutionDate;
        this.orderValue = orderValue;
        this.orderState = orderState;
        this.receivementMethod = receivementMethod;
    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public LocalDate getPlanedExecutionDate() {
        return planedExecutionDate;
    }

    public void setPlanedExecutionDate(LocalDate planedExecutionDate) {
        this.planedExecutionDate = planedExecutionDate;
    }

    public LocalDate getRealExecutionDate() {
        return realExecutionDate;
    }

    public void setRealExecutionDate(LocalDate realExecutionDate) {
        this.realExecutionDate = realExecutionDate;
    }

    public int getOrderValue() {
        return orderValue;
    }

    public void setOrderValue(int orderValue) {
        orderValue = orderValue;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) throws Exception {

        if(orderState == OrderState.CANCELED){
            if(planedExecutionDate.minusDays(7).isBefore(LocalDate.now())){
                throw new Exception("Its to late to cancel order.");
            }
        }

        this.orderState = orderState;
    }

    public ReceivementMethod getReceivementMethod() {
        return receivementMethod;
    }

    public void setReceivementMethod(ReceivementMethod receivementMethod) {
        this.receivementMethod = receivementMethod;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean checkCorrectness(){
        for (Quantity quantity1 : quantity) {
            if(quantity1.getM2()<0){
                return false;
            }
            if(quantity1.getM2()>quantity1.getParquet().getM2Left()){
                return false;
            }
        }
        return true;
    }

    @ManyToMany
    private Collection<Courier> deliveredBy;

    public Collection<Courier> getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(Collection<Courier> deliveredBy) throws Exception {
        if( deliveredBy.size()<=Courier.getMaxContractors()) {
            this.deliveredBy = deliveredBy;
        }else{
            throw  new Exception("number of Contractors must be smaller than "+ Courier.getMaxContractors());
        }
    }


    @ManyToOne
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    @OneToOne(mappedBy = "order")
    private Invoice invoice;

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    @ManyToOne
    private ConsumerService helpedBy;

    public ConsumerService getHelpedBy() {
        return helpedBy;
    }

    public void setHelpedBy(ConsumerService helpedBy) {
        this.helpedBy = helpedBy;
    }

    @OneToMany(mappedBy = "order")
    private Collection<Quantity> quantity = new ArrayList<>();

    public Collection<Quantity> getQuantity() {
        return quantity;
    }

    public void setQuantity(Collection<Quantity> quantity) {
        this.quantity = quantity;
    }

    public void addParquet(Parquet parquet, double quantity) throws Exception {
        Quantity quan = new Quantity(quantity);

        quan.setOrder(this);
        quan.setParquet(parquet);
    }
}
