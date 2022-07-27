package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StaticValues {

    @Id
    private Long id;

    private int bonusLanguage;
    private int bonusC;
    private int maxCouriers;
    private int minimalPriceM2;
    private double minParquetSize;

    public StaticValues(int bonusLanguage, int bonusC, int maxCouriers, int minimalPriceM2, double minParquetSize) {
        this.bonusLanguage = bonusLanguage;
        this.bonusC = bonusC;
        this.maxCouriers = maxCouriers;
        this.minimalPriceM2 = minimalPriceM2;
        this.minParquetSize = minParquetSize;
        this.id=0l;
    }

    public StaticValues() {

    }

    public int getBonusLanguage() {
        return bonusLanguage;
    }

    public void setBonusLanguage(int bonusLanguage) {
        this.bonusLanguage = bonusLanguage;
    }

    public int getBonusC() {
        return bonusC;
    }

    public void setBonusC(int bonusC) {
        this.bonusC = bonusC;
    }

    public int getMaxCouriers() {
        return maxCouriers;
    }

    public void setMaxCouriers(int maxCouriers) {
        this.maxCouriers = maxCouriers;
    }

    public int getMinimalPriceM2() {
        return minimalPriceM2;
    }

    public void setMinimalPriceM2(int minimalPriceM2) {
        this.minimalPriceM2 = minimalPriceM2;
    }

    public double getMinParquetSize() {
        return minParquetSize;
    }

    public void setMinParquetSize(double minParquetSize) {
        this.minParquetSize = minParquetSize;
    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
