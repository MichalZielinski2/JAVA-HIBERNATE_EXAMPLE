package model;

import javax.persistence.*;
import java.util.List;

@Entity
public class IndividualCustomer extends Customer{

    @ElementCollection
    private List<String> name;
    private String surname;
    private String email;
    private String phoneNumber;
    @Id
    @GeneratedValue
    private Long id;

    public IndividualCustomer(List<String> name, String surname, String email, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public IndividualCustomer() {

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @OneToOne
    private Person is;

    public Person getIs() {
        return is;
    }

    public void setIs(Person is) {
        this.is = is;
    }
}
