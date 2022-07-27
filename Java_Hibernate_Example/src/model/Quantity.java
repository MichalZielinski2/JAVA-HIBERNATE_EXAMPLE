package model;

import javax.persistence.*;

@Entity
public class Quantity {
    private Long id;
    private double m2;

    public Quantity(double m2) throws Exception {
        setM2(m2);
    }

    public Quantity() {

    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public double getM2() {
        return m2;
    }

    public void setM2(double m2) throws Exception {
        if(Parquet.getMinimalSize()>m2){
            throw new Exception("too small parquet");
        }else {
            this.m2 = m2;
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }


    private Parquet parquet;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    public Parquet getParquet() {
        return parquet;
    }

    public void setParquet(Parquet parquet) {
        this.parquet = parquet;
    }


    private OrderN orderN;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    public OrderN getOrder() {
        return orderN;
    }

    public void setOrder(OrderN orderN) {
        this.orderN = orderN;
    }
}
