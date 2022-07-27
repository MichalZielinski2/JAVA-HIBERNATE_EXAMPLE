package model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Availability {

    private boolean available;
    private Long id;
    private int price;

    public Availability(boolean available, int price, Supplier supplier, Parquet parquet) {
        this.available = available;
        this.price = price;
        this.supplier = supplier;
        this.parquet = parquet;
    }

    public Availability() {

    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }


    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    private Parquet parquet;

    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    public Parquet getParquet() {
        return parquet;
    }

    public void setParquet(Parquet parquet) {
        this.parquet = parquet;
    }
}
