package main;

import java.util.ArrayList;
import java.util.List;

import gui.*;
import model.*;

public class Main {
    public static void main(String[] args) throws Exception {


        prepareExample();
        showGui();

    }

    private static void showGui() {

        App.main(null);

    }

    private static void prepareExample() throws Exception {

        var parq = new Parquet("Bursztynowy",30 , "nakladany" , 40);
        var woodType = new WoodKind("dąb", 4, "jasny braz");
        var woodType2 = new WoodKind("sosna", 5, "sosnowy");
        var list = new ArrayList<Parquet>();
        list.add(parq);
        woodType.setParquets(list);
        parq.setWoodKind(woodType);
        ServerConnection.getInstance().save(woodType);
        ServerConnection.getInstance().save(woodType2);


        var list2 = ServerConnection.getInstance().session.createQuery("from WoodKind").list();
        System.out.println(list2);

        ServerConnection.getInstance().save(parq);

        IndividualCustomer customer = new IndividualCustomer(List.of("Michał"), "zieliński", "aixdad", "123493");
        customer.save();

        StaticValues staticValues = new StaticValues(200, 200, 7, 0, 0);
        staticValues.save();

        var parq2 = new Parquet("Piękny",20 , "dostawiany" , 1000);
        parq2.setWoodKind(woodType);
        parq2.save();

        var parq3 = new Parquet("Sosna",20 , "Przekreślany" , 1000);
        parq3.setWoodKind(woodType2);
        parq3.save();

        var parq4 = new Parquet("ddddd",20 , "Przekreślany" , 1000);
        parq4.setWoodKind(woodType);
        parq4.save();
        for(int i = 0; i < 10; i++) {
            var parq9 = new Parquet("Piękny"+i, 20, "dostawiany", 1000);
            parq9.setWoodKind(woodType);
            parq9.save();
        }


        //ServerConnection.getInstance().session.getTransaction().commit();

    }
}
