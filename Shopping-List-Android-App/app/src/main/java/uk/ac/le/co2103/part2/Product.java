package uk.ac.le.co2103.part2;

import java.io.Serializable;

public class Product implements Serializable {

    private String name; //unique

    private int quantity; //text field input type - number

    private String unit; //Unit, Kg, Litre implemented in UI using Spinner view

    public Product(String name, int quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
