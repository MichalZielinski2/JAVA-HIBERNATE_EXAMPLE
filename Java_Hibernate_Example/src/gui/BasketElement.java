package gui;

import model.Parquet;

import java.util.ArrayList;

public class BasketElement {

    public Parquet parquet;
    public Double quantity;

    public BasketElement(Parquet addedParquet, Double meters) {
        parquet = addedParquet;
        quantity = meters;

    }



}
