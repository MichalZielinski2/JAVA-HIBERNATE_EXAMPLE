package model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class ConsumerService extends Employee{

    @ElementCollection
    private List<String> languages;

    public ConsumerService() {
    }

    public ConsumerService(List<String> languages) {
        this.languages = languages;
    }



    public void save(){
        ServerConnection.getInstance().save(this);
    }




    public static int getBonusFromLanguage() {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        return values.get(0).getBonusLanguage();
    }

    public static void setBonusFromLanguage(int bonusFromLanguage) {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        values.get(0).setBonusLanguage(bonusFromLanguage);
        values.get(0).save();
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @Transient
    public boolean doesNowLanguage(){

        if(languages == null){
            return false;
        }
        return  !(languages.isEmpty());
    }

    @Transient
    public int getSalary(){
        return (super.getSalary()+ (doesNowLanguage()? getBonusFromLanguage(): 0));
    }


    @OneToMany(mappedBy = "helpedBy")
    private Collection<OrderN> helpedWith;

    public Collection<OrderN> getHelpedWith() {
        return helpedWith;
    }

    public void setHelpedWith(Collection<OrderN> helpedWith) {
        this.helpedWith = helpedWith;
    }
}
