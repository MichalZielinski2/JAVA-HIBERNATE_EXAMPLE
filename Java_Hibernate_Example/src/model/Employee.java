package model;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Employee extends Person{

    private LocalDate employmentDate;
    private int hourlyRate;
    private float partTime; // what part of full time employee is working

    public Employee(List<String> name, String surname, String email, String phoneNumber, LocalDate employmentDate, int hourlyRate, float partTime) {
        super(name, surname, email, phoneNumber);
        this.employmentDate = employmentDate;
        this.hourlyRate = hourlyRate;
        this.partTime = partTime;
    }

    public Employee() {

    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    @Transient
    public int getSalary(){
        return (int)(hourlyRate*partTime*160); //160 number of working h in one month
    }

    public static double getAverageSalary(){
        List<Employee> employees = ServerConnection.getInstance().session.createQuery("from Employee").list();

        return employees.stream().mapToInt(e->e.getSalary()).average().getAsDouble();
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public float getPartTime() {
        return partTime;
    }

    public void setPartTime(float partTime) {
        this.partTime = partTime;
    }

}
