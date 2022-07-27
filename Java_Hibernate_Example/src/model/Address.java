package model;

import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Address {
    private Long id;

    String city;
    String street;
    String flatNumber;
    String postalCode;

    public Address(String city, String street, String flatNumber, String postalCode) {
        this.city = city;
        this.street = street;
        this.flatNumber = flatNumber;
        this.postalCode = postalCode;

    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public Address() {

    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFlatNumber() {
        return flatNumber;
    }

    public void setFlatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }




    private Collection<Supplier> suppliers;

    @OneToMany(mappedBy = "address")
    public Collection<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(Collection<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    private Collection<Customer> customers;

    @OneToMany(mappedBy = "adress")
    public Collection<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Collection<Customer> customers) {
        this.customers = customers;
    }
}
