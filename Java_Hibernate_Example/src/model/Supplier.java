package model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Supplier {
    private String nip; // todo unique
    private String name;
    private String deliveryTime;
    private Long id;

    public Supplier(String nip, String name, String deliveryTime) throws Exception {
        setNip(nip);
        this.name = name;
        this.deliveryTime = deliveryTime;
    }

    public Supplier() {

    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }


    private Address address;

    @ManyToOne(optional = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private Collection<Availability> parquets;

    @OneToMany(mappedBy = "supplier")
    public Collection<Availability> getParquets() {
        return parquets;
    }

    public void setParquets(Collection<Availability> parquets) {
        this.parquets = parquets;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) throws Exception {
        var query = ServerConnection.getInstance().session.
                createQuery("select count(*) from Supplier where nip =:nip");
        query.setParameter("nip",nip);
        Long count = (Long)query.uniqueResult();

        if(count > 0){
            throw new Exception("NIP must be unique");
        }else{
            this.nip = nip;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
