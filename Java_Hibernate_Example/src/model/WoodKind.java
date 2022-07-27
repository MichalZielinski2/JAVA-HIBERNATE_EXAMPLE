package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class WoodKind {
    private Long id;

    private String name;
    private int hardness;
    private String color;

    public WoodKind(String name, int hardness, String color) throws Exception {
        setName(name);
        this.hardness = hardness;
        this.color = color;
    }

    public WoodKind() {

    }

    public void save(){
        ServerConnection.getInstance().save(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        this.name = name;
    }

    public int getHardness() {
        return hardness;
    }

    public void setHardness(int hardness) throws Exception {
        if(hardness>6 || hardness <1){
            throw new Exception("hardness must be between 1 and 6");
        }
        this.hardness = hardness;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Long getId() {
        return id;
    }


    private Collection<Parquet> parquets;

    @OneToMany(mappedBy = "woodKind")
    public Collection<Parquet> getParquets() {
        return parquets;
    }

    public void setParquets(Collection<Parquet> parquets) {
        this.parquets = parquets;
    }
}
