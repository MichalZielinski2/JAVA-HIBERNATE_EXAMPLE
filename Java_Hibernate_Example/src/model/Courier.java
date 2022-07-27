package model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;

@Entity
public class Courier extends Employee{

    private DrivingLicenseCategory drivingLicense;

    public Courier() {

    }

    public enum DrivingLicenseCategory{
        A,B,C
    }




    public Courier(DrivingLicenseCategory drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public DrivingLicenseCategory getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(DrivingLicenseCategory drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public static int getBonusCategoryC() {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        return values.get(0).getBonusC();

    }

    public static void setBonusCategoryC(int bonusCategoryC) {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        values.get(0).setBonusC(bonusCategoryC);
        values.get(0).save();
    }

    public static int getMaxContractors() {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        return values.get(0).getMaxCouriers();
    }

    public static void setMaxContractors(int maxContractors) {
        List<StaticValues> values = ServerConnection.getInstance().session.createQuery("from StaticValues where id = 0").list();
        values.get(0).setMaxCouriers(maxContractors);
        values.get(0).save();
    }

    @Transient
    public int getSalary(){
        return super.getSalary()+ (drivingLicense == DrivingLicenseCategory.C? getBonusCategoryC(): 0);
    }

    @ManyToMany(mappedBy = "deliveredBy")
    private Collection<OrderN> orderN;

    public Collection<OrderN> getOrder() {
        return orderN;
    }

    public void setOrder(Collection<OrderN> orderN) {
        this.orderN = orderN;
    }
}
