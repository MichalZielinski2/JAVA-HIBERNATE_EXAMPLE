package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class Parquet {

    private String name;
    private int priceM2;
    private String type;
    private Long id;
    private double m2Left;

    public Parquet(String name, int priceM2, String type, double m2Left) throws Exception {
        setName(name);
        this.priceM2 = priceM2;
        this.type = type;
        this.m2Left = m2Left;
    }

    public Parquet() {

    }


    public void save(){
        ServerConnection.getInstance().save(this);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if(name.equals(this.name) ) return;
        var query = ServerConnection.getInstance().session.
                createQuery("select count(*) from Parquet where name =:name");
        query.setParameter("name",name);
        Long count = (Long)query.uniqueResult();

        if(count > 0){
            throw new Exception("Name of Parquet type must be unique");
        }else{
            this.name = name;
        }
    }

    public int getPriceM2() {
        return priceM2;
    }

    public void setPriceM2(int priceM2) throws Exception {
        if (priceM2 >= getMinimalPriceM2()) {
            this.priceM2 = priceM2;
        }else{
            throw new Exception("Cena musi być większa niż minimalna cena");
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static int getMinimalPriceM2() {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        return values.get(0).getMinimalPriceM2();
    }

    public static void setMinimalPriceM2(int minimalPriceM2) {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        values.get(0).setMinimalPriceM2(minimalPriceM2);
        values.get(0).save();
    }

    public static double getMinimalSize() {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        return values.get(0).getMinParquetSize();
    }

    public static void setMinimalSize(double minimalSize) {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        values.get(0).setMinParquetSize(minimalSize);
        values.get(0).save();
    }

    public double getM2Left() {
        return m2Left;
    }

    public void setM2Left(double m2Left) {
        this.m2Left = m2Left;
    }

    @Transient
    public boolean isParquetAvailable(){
        return getM2Left() >= getMinimalSize();
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }


    private Collection<Availability> suppliers;

    @OneToMany(mappedBy = "parquet")
    public Collection<Availability> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Collection<Availability> suppliers) {
        this.suppliers = suppliers;
    }

    private WoodKind woodKind;

    @ManyToOne(optional = false)
    public WoodKind getWoodKind() {
        return woodKind;
    }

    public void setWoodKind(WoodKind woodKind) {
        this.woodKind = woodKind;
    }

    private Collection<Quantity> quantity;

    @OneToMany(mappedBy = "parquet")
    public Collection<Quantity> getQuantity() {
        return quantity;
    }

    public void setQuantity(Collection<Quantity> quantity) {
        this.quantity = quantity;
    }
}
