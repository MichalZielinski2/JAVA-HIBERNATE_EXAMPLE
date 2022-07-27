package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public abstract class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Transient
    public int getDiscountPercent(){
        if(orderNS.stream().count()>9){
            return 10;
        }else {
            return 0; // TODO
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    @OneToMany(mappedBy = "customer")
    private Collection<OrderN> orderNS;

    public Collection<OrderN> getOrders() {
        return orderNS;
    }

    public void setOrders(Collection<OrderN> orderNS) {
        this.orderNS = orderNS;
    }

    @ManyToOne
    private Address adress;

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }

    public void sendPromoInfoToAllCustomers(){
        List<Customer> customers =ServerConnection.getInstance().session.
                createQuery("from Customer").list();

        for (Customer cus: customers) {
            /*
             tutaj wpisałbym funkcję która wysyła maila. Nie zrobię tego ponieważ
             prawdopodobnie wymagało by to przeczytania dokumentacji jakiegoś serwisu
             do wysyłąmia maili, a żeby w ogóle zadziałało to musiał bym zapłacić.
            */
        }


    }

}
