package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@Entity
public class InstitutionalClient extends Customer{

    private String nip;
    private String phoneNumber;
    private String emailAddress;
    private String mailAddress;

    public InstitutionalClient(String nip, String phoneNumber, String emailAddress, String mailAddress) throws Exception {
        setNip(nip);
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.mailAddress = mailAddress;
    }

    public InstitutionalClient() {

    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws Exception {
        if(phoneNumber == null && this.emailAddress == null && this.mailAddress == null){
            throw new Exception("Wymagana jest minimum jedna forma kontaktu");
        }

        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) throws Exception {

        if(this.phoneNumber == null && emailAddress == null && this.mailAddress == null){
            throw new Exception("Wymagana jest minimum jedna forma kontaktu");
        }

        this.emailAddress = emailAddress;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) throws Exception {
        if(this.phoneNumber == null && this.emailAddress == null && mailAddress == null){
            throw new Exception("Wymagana jest minimum jedna forma kontaktu");
        }
        this.mailAddress = mailAddress;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) throws Exception {
        var query = ServerConnection.getInstance().session.
                createQuery("select count(*) from InstitutionalClient where nip =:nip");
        query.setParameter("nip",nip);
        Long count = (Long)query.uniqueResult();

        if(count > 0){
            throw new Exception("NIP must be unique");
        }else{
            this.nip = nip;
        }
    }

}
